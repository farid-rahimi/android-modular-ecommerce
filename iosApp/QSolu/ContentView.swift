import SwiftUI
import sharedKit
import sharedUIKit
import Combine

@MainActor
final class ProductsViewModel: ObservableObject {
    @Published var products: [IosProductItem] = []
    @Published var isLoading = false
    @Published var errorMessage: String?

    private let bridge = IosProductsBridge()

    func loadProducts() {
        guard !isLoading else { return }

        isLoading = true
        errorMessage = nil

        bridge.loadProducts(onResult: { items in
            self.products = items
            self.isLoading = false
        }, onError: { message in
            self.errorMessage = message
            self.isLoading = false
        })
    }
}

struct ContentView: View {
    @StateObject private var viewModel = ProductsViewModel()

    var body: some View {
        NavigationStack {
            Group {
                if viewModel.isLoading {
                    ProgressView("Loading products...")
                } else if let error = viewModel.errorMessage {
                    VStack(spacing: 12) {
                        Text(error)
                            .foregroundColor(.red)
                            .multilineTextAlignment(.center)
                        Button("Retry") {
                            viewModel.loadProducts()
                        }
                    }
                    .padding()
                } else {
                    SharedProductsComposableView(items: viewModel.products)
                }
            }
            .navigationTitle("Products")
        }
        .onAppear {
            viewModel.loadProducts()
        }
    }
}

struct SharedProductsComposableView: UIViewControllerRepresentable {
    let items: [IosProductItem]

    func makeCoordinator() -> Coordinator {
        Coordinator()
    }

    func makeUIViewController(context: Context) -> UIViewController {
        let host = IosProductListComposeHost(
            initialItems: items
        )
        context.coordinator.host = host
        return host.viewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        context.coordinator.host?.updateProducts(items: items)
    }

    final class Coordinator {
        var host: IosProductListComposeHost?
    }
}

#Preview {
    ContentView()
}
