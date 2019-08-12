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
            let value = UserDefaults.standard.string(forKey: "history") ?? ""
            return value
                .components(separatedBy: ",")
                .map { face in Int32(face) }
                .filter { face in face != nil}
                .enumerated()
                .map { (arg) -> Roll in Roll(id: Int32(arg.offset), face: arg.element!) }
        }
        
        let putHistory = { (history: [Roll]) -> KotlinUnit in
            let value = history
                .map { roll in String(roll.face) }
                .joined(separator: ",")
            
            UserDefaults.standard.set(value, forKey: "history")
            
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
