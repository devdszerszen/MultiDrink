import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject private(set) var viewModel: ViewModel
    
	var body: some View {
        Text(viewModel.text)
	}
}

extension ContentView {
    class ViewModel: ObservableObject {
        @Published var text = "Loading..."
        private let drinksRepository = RepositoryModule().drinkRepository
        init() {
            drinksRepository.getRandomDrink { drink, error in
                DispatchQueue.main.async {
                    if let drink = drink {
                        self.text = drink.name
                    } else {
                        self.text = error?.localizedDescription ?? "Unknown error"
                    }
                }
            }
        }
    }
}

//struct ContentView_Previews: PreviewProvider {
//	static var previews: some View {
//		ContentView()
//	}
//}
