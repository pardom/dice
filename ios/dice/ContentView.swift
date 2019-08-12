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
            Header(props: props, dispatch: dispatch)
            Spacer()
            Content(props: props, dispatch: dispatch)
            Spacer()
            Footer(rolls: props.rolls)
        }
    }
    
    struct Header: View {
        let props: Dice.Props
        let dispatch: (Dice.Msg) -> KotlinUnit
        
        var body: some View {
            HeaderReset(props: props, dispatch: dispatch)
        }
    }
    
    struct HeaderReset: View {
        let props: Dice.Props
        let dispatch: (Dice.Msg) -> KotlinUnit
        
        var body: some View {
            HStack {
                Spacer()
                if !self.props.rolls.isEmpty {
                    Image(systemName: "clear.fill")
                        .resizable()
                        .frame(width: 25, height: 25)
                        .padding(15)
                        .tapAction { self.dispatch(self.props.onUserClickedResetButton()) }
                }
            }
        }
    }
    
    struct Content: View {
        let props: Dice.Props
        let dispatch: (Dice.Msg) -> KotlinUnit
        
        var body: some View {
            Image(systemName: "\(props.rolls.last?.face ?? 3).square")
                .resizable()
                .aspectRatio(1.0, contentMode: .fit)
                .rotationEffect(.degrees(Double(self.props.rollCount) * 360))
                .tapAction { self.dispatch(self.props.onUserClickedRollButton()) }
        }
    }
    
    struct Footer: View {
        let rolls: [Roll]
        
        var body: some View {
            rolls.isEmpty ?
                AnyView(FooterHelp()) :
                AnyView(FooterHistory(rolls: rolls))
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
            HStack {
                FooterHistoryRoll(roll: rolls.count > 1 ? rolls.reversed()[1] : nil)
                FooterHistoryRoll(roll: rolls.count > 2 ? rolls.reversed()[2] : nil)
                FooterHistoryRoll(roll: rolls.count > 3 ? rolls.reversed()[3] : nil)
            }
        }
    }
    
    struct FooterHistoryRoll: View {
        let roll: Roll?
        
        var body: some View {
            Image(systemName: "\(roll?.face ?? 0).square")
                .resizable()
                .frame(width: roll != nil ? 50 : 0, height: 50)
        }
    }
}
