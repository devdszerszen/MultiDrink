//
//  ImageExt.swift
//  iosApp
//
//  Created by Damian Szerszeń on 29/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

extension UIImage {
    
    func fromUrl(url: String?) -> UIImage {
        do {
            guard let url = URL(string: url ?? "") else {
                return UIImage(named: "placeholder")!
            }
            let data: Data = try Data(contentsOf: url)
            
            return UIImage(data: data) ?? UIImage(named: "placeholder")!
            
        } catch {
            print(error)
        }
        return UIImage(named: "placeholder")!
    }
}
