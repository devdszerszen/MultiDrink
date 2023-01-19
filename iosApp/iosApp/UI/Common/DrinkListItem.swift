//
//  DrinkListItem.swift
//  iosApp
//
//  Created by Damian Szerszeń on 03/12/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct DrinkListItem: View {
    let drink: Drink
    
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text(drink.name)
                    .font(.title)
            }
            .padding()
            Spacer()
        }
    }
}

struct DrinkListItem_Previews: PreviewProvider {
    static var previews: some View {
        let drink = Drink(id: "id", name: "name", image: "image")
        DrinkListItem(drink: drink)
    }
}
