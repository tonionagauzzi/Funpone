//
//  ShortcutWidgetLiveActivity.swift
//  ShortcutWidget
//
//  Created by tonionagauzzi on 2023/11/19.
//

import ActivityKit
import WidgetKit
import SwiftUI

struct ShortcutWidgetAttributes: ActivityAttributes {
    public struct ContentState: Codable, Hashable {
        // Dynamic stateful properties about your activity go here!
        var emoji: String
    }

    // Fixed non-changing properties about your activity go here!
    var name: String
}

struct ShortcutWidgetLiveActivity: Widget {
    var body: some WidgetConfiguration {
        ActivityConfiguration(for: ShortcutWidgetAttributes.self) { context in
            // Lock screen/banner UI goes here
            VStack {
                Text("Hello \(context.state.emoji)")
            }
            .activityBackgroundTint(Color.cyan)
            .activitySystemActionForegroundColor(Color.black)

        } dynamicIsland: { context in
            DynamicIsland {
                // Expanded UI goes here.  Compose the expanded UI through
                // various regions, like leading/trailing/center/bottom
                DynamicIslandExpandedRegion(.leading) {
                    Text("Leading")
                }
                DynamicIslandExpandedRegion(.trailing) {
                    Text("Trailing")
                }
                DynamicIslandExpandedRegion(.bottom) {
                    Text("Bottom \(context.state.emoji)")
                    // more content
                }
            } compactLeading: {
                Text("L")
            } compactTrailing: {
                Text("T \(context.state.emoji)")
            } minimal: {
                Text(context.state.emoji)
            }
            .widgetURL(URL(string: "http://www.apple.com"))
            .keylineTint(Color.red)
        }
    }
}

extension ShortcutWidgetAttributes {
    fileprivate static var preview: ShortcutWidgetAttributes {
        ShortcutWidgetAttributes(name: "World")
    }
}

extension ShortcutWidgetAttributes.ContentState {
    fileprivate static var smiley: ShortcutWidgetAttributes.ContentState {
        ShortcutWidgetAttributes.ContentState(emoji: "😀")
     }
     
     fileprivate static var starEyes: ShortcutWidgetAttributes.ContentState {
         ShortcutWidgetAttributes.ContentState(emoji: "🤩")
     }
}

#Preview("Notification", as: .content, using: ShortcutWidgetAttributes.preview) {
   ShortcutWidgetLiveActivity()
} contentStates: {
    ShortcutWidgetAttributes.ContentState.smiley
    ShortcutWidgetAttributes.ContentState.starEyes
}
