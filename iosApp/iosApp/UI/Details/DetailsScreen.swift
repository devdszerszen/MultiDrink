//
//  DetailsScreen.swift
//  iosApp
//
//  Created by Damian Szerszeń on 05/02/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct DetailsScreen: View {
    
    let drink: Drink
    
    @ObservedObject private var viewModel = ViewModel()
    
    var body: some View {
        VStack {
            var url = (drink.image != nil ? URL(string: drink.image!) : nil)
            AsyncImage(url: url, content: { image in
                        image.resizable()
                    }, placeholder: {
                        ProgressView()
                    })
                        .frame(width: 200, height: 200)
                        .clipShape(Circle())
            Text(drink.name)
            
        }
    }
}

extension DetailsScreen {
    class ViewModel: ObservableObject {
        
    }
}

struct DetailsScreen_Previews: PreviewProvider {
    static var previews: some View {
        let drink = Drink(id: "id", name: "name", image: "https://www.thecocktaildb.com/images/media/drink/xwxyux1441254243.jpg")
        DetailsScreen(drink: drink)
    }
}
