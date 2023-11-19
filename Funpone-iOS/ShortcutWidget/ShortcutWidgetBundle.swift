//
//  ShortcutWidgetBundle.swift
//  ShortcutWidget
//
//  Created by tonionagauzzi on 2023/11/19.
//

import WidgetKit
import SwiftUI

@main
struct ShortcutWidgetBundle: WidgetBundle {
    var body: some Widget {
        ShortcutWidget()
        ShortcutWidgetLiveActivity()
    }
}
