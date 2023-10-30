//
//  Item.swift
//  Funpone
//
//  Created by tonionagauzzi on 2023/10/30.
//

import Foundation
import SwiftData

@Model
final class Item {
    var timestamp: Date
    
    init(timestamp: Date) {
        self.timestamp = timestamp
    }
}
