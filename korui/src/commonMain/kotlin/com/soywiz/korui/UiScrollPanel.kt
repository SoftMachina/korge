package com.soywiz.korui

interface UiScrollPanel : UiContainer {
}

fun UiContainer.scrollPanel(block: UiScrollPanel.() -> Unit): UiScrollPanel {
    return factory.createScrollPanel().also { it.parent = this }.also(block)
}
