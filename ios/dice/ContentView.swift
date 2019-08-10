//
//  ContentView.swift
//  dice
//
//  Created by Michael Pardo on 8/5/19.
//  Copyright © 2019 Michael Pardo. All rights reserved.
//

import SwiftUI
import common

struct ContentView: View {
    let props: Dice.Props
    let dispatch: (Dice.Msg) -> KotlinUnit
    
    var body: some View {
        VStack {
            Header(rolls: props.rolls)
            if (props.rolls.count > 0) {
                Content(rolls: props.rolls, rollCount: props.rollCount)
            }
            Footer(rolls: props.rolls)
        }
    }
    
    struct Header: View {
        let rolls: [Roll]
        
        var body: some View {
            HeaderReset()
        }
    }
    
    struct HeaderReset: View {
        var body: some View {
            HStack {
                Text("reset")
            }
        }
    }
    
    struct Content: View {
        let rolls: [Roll]
        let rollCount: Int32
        
        var body: some View {
            Text("Last Roll")
        }
    }
    
    struct Footer: View {
        let rolls: [Roll]
        
        @ViewBuilder
        var body: some View {
            if (rolls.isEmpty) {
                FooterHelp()
            }
            else {
                FooterHistory(rolls: rolls)
            }
        }
    }
    
    struct FooterHelp: View {
        var body: some View {
            Text("Tap to roll")
        }
    }
    
    struct FooterHistory: View {
        let rolls: [Roll]
        
        var body: some View {
            Text("History")
        }
    }
    
    struct FooterHistoryRoll: View {
        let roll: Roll
        
        var body: some View {
            Spacer()
        }
    }
}
