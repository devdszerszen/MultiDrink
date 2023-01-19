//
//  SearchView.swift
//  iosApp
//
//  Created by Damian Szerszeń on 03/12/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SearchScreen: View {
    
    @ObservedObject private var viewModel = ViewModel()
    
    var body: some View {
        VStack {
            TextField("Input drink name...", text: $viewModel.input)
                .padding(.horizontal)
                .textFieldStyle(RoundedBorderTextFieldStyle())
            if let errorMessage = viewModel.errorMessage, !errorMessage.isEmpty {
                Text(errorMessage).foregroundColor(.red)
            }
            if viewModel.isInitialState {
                VStack {
                    Image(systemName: "heart.fill").imageScale(.large)
                    Text("Delicious drinks are waiting for you, check it out!")
                        .multilineTextAlignment(.center)
                }.padding(32)
                
            } else {
                List(viewModel.drinks, id: \.id) { drink in
                    DrinkListItem(drink: drink)
                }
            }
            Spacer()
        }
    }
}

extension SearchScreen {
    class ViewModel: ObservableObject {
        @Published var drinks: [Drink] = []
        @Published var errorMessage: String? = nil
        @Published var isInitialState: Bool = true
        var input: String = "" {
            didSet {
                onInputChanged()
            }
        }
        
        private let drinksRepository = RepositoryModuleDI().getDrinksRepository()
        
        init() {}
        
        func onInputChanged() {
            errorMessage = nil
            if input.isEmpty {
                drinks = []
                isInitialState = true
            } else {
                isInitialState = false
                search()
            }
        }
        
        private func search() {
            drinksRepository.findByName(name:input) { response, error in
                DispatchQueue.main.async {
                    if let response = response {
                        response.fold(
                            onSuccess: { data in
                                self.drinks = data as? [Drink] ?? []
                                self.errorMessage = nil
                                return
                            }, onFailure: { error in
                                self.drinks = []
                                self.errorMessage = error.message
                                return
                            }
                        )
                    }
                }
            }
        }
    }
}

struct SearchScreen_Previews: PreviewProvider {
    static var previews: some View {
        SearchScreen()
    }
}
