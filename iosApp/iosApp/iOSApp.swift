import SwiftUI
import shared

@main
struct iOSApp: App {
    init() {
        DiKt.doInitKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}