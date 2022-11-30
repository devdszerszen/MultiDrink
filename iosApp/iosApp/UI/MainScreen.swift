//
//  MainScreen.swift
//  iosApp
//
//  Created by Damian Szerszeń on 29/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MainScreen: View {
    
    @ObservedObject private var viewModel = ViewModel()
    
    var body: some View {
        VStack {
            if let drink = viewModel.drink {
                Image(uiImage: UIImage().fromUrl(url: drink.image))
                    .resizable()
                    .padding(.top)
                    .scaledToFill()
                    .frame(width: 256, height: 256, alignment: .center)
                    .clipped()
                    .clipShape(Circle())
                    .overlay(Circle().stroke(Color.white, lineWidth: 4))
                    .shadow(radius: /*@START_MENU_TOKEN@*/10/*@END_MENU_TOKEN@*/)
                Text(drink.name)
            }
            if let message = viewModel.message {
                Text(message)
                TextField("Input drink name", text:$viewModel.input)
                Button(action: {
                    viewModel.search()
                }, label: {
                    Text("Search")
                })
            }
        }
    }
}

extension MainScreen {
    class ViewModel: ObservableObject {
        @Published var drink: Drink? = nil
        @Published var message: String? = "Loading..."
        var input: String = ""
        
        private let drinksRepository = RepositoryModuleDI().getDrinksRepository()
        
        init() {
//            drinksRepository.getRandomDrink { drink, error in
//                DispatchQueue.main.async {
//                    if let drink = drink {
//                        self.drink = drink
//                        self.message = nil
//                    } else {
//                        self.message = error?.localizedDescription ?? "Unknown error"
//                    }
//                }
//            }
        }
        
        func search() {
            print(self.input)
            drinksRepository.getRandomDrink { drink, error in
                DispatchQueue.main.async {
                    if let drink = drink {
                        self.drink = drink
                        self.message = nil
                    } else {
                        self.message = error?.localizedDescription ?? "Unknown error"
                    }
                }
            }
        }
    }
}
