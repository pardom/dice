//
//  SceneDelegate.swift
//  dice
//
//  Created by Michael Pardo on 8/5/19.
//  Copyright © 2019 Michael Pardo. All rights reserved.
//

import UIKit
import SwiftUI
import common

class SceneDelegate: UIResponder, UIWindowSceneDelegate {

    var window: UIWindow?

    func scene(_ scene: UIScene, willConnectTo session: UISceneSession, options connectionOptions: UIScene.ConnectionOptions) {
        
        let getHistory = { () -> [Roll] in
            return []
        }
        
        let putHistory = { (history: [Roll]) -> KotlinUnit in
            return KotlinUnit()
        }
        
        let render = { (props: Dice.Props, dispatch: @escaping (Dice.Msg) -> KotlinUnit) -> Any? in
            if let windowScene = scene as? UIWindowScene {
                let window = UIWindow(windowScene: windowScene)
                window.rootViewController = UIHostingController(rootView: ContentView(props: props, dispatch: dispatch))
                self.window = window
                window.makeKeyAndVisible()
            }
            return KotlinUnit()
        }
        
        let _ = Dice().runtime(
            init: Dice().makeInit(getHistory),
            update: Dice().makeUpdate(putHistory),
            render: render
        )
        

    }

    func sceneDidDisconnect(_ scene: UIScene) {
    }

    func sceneDidBecomeActive(_ scene: UIScene) {
    }

    func sceneWillResignActive(_ scene: UIScene) {
    }

    func sceneWillEnterForeground(_ scene: UIScene) {
    }

    func sceneDidEnterBackground(_ scene: UIScene) {
    }

}
