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
        
        private let drinksRepository = RepositoryModuleDI().drinksRepository
        private var searchTask: Cancellable? = nil
        
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
            searchTask?.cancel()
            searchTask = drinksRepository.findByName(name: input).handleIos { drinksList in
                self.drinks = drinksList as? [Drink] ?? []
                self.errorMessage = nil
            } onError: { error in
                self.drinks = []
                self.errorMessage = error.message
            }
        }
    }
}

struct SearchScreen_Previews: PreviewProvider {
    static var previews: some View {
        SearchScreen()
    }
}
