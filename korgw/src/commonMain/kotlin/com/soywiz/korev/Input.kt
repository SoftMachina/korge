package com.soywiz.korev

import com.soywiz.kds.*
import com.soywiz.kds.iterators.*
import com.soywiz.kmem.*
import com.soywiz.korma.geom.*
import kotlin.math.*

enum class MouseButton(val id: Int, val bits: Int = 1 shl id) {
	LEFT(0), MIDDLE(1), RIGHT(2), BUTTON3(3),
    BUTTON4(4), BUTTON5(5), BUTTON6(6), BUTTON7(7),
    BUTTON_WHEEL(8),
    BUTTON_UNKNOWN(10),
    NONE(11, bits = 0);

    val isLeft get() = this == LEFT
    val isMiddle get() = this == MIDDLE
    val isRight get() = this == RIGHT

    fun pressedFromFlags(flags: Int): Boolean = (flags and this.bits) != 0

    companion object {
        val MAX = NONE.ordinal + 1
		val BUTTONS = values()
		operator fun get(id: Int) = BUTTONS.getOrElse(id) { BUTTON_UNKNOWN }
	}
}

enum class Key {
	SPACE, APOSTROPHE, COMMA, MINUS, PLUS, PERIOD, SLASH,
	N0, N1, N2, N3, N4, N5, N6, N7, N8, N9, N11, N12,
    N3D_MODE,
	SEMICOLON, EQUAL,
    AT,
	A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z,
	LEFT_BRACKET, BACKSLASH, RIGHT_BRACKET, GRAVE_ACCENT,
	WORLD_1, WORLD_2,
	ESCAPE,
	META,
	ENTER, TAB, BACKSPACE, INSERT, DELETE,
	RIGHT, LEFT, DOWN, UP,
	PAGE_UP, PAGE_DOWN, FUNCTION, HELP, MUTE, VOLUME_DOWN, VOLUME_UP, VOLUME_MUTE,
	HOME, END,
	CAPS_LOCK, SCROLL_LOCK, NUM_LOCK,
	PRINT_SCREEN, PAUSE,
	F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12,
	F13, F14, F15, F16, F17, F18, F19, F20, F21, F22, F23, F24, F25,
	KP_0, KP_1, KP_2, KP_3, KP_4, KP_5, KP_6, KP_7, KP_8, KP_9,
	KP_DECIMAL, KP_DIVIDE, KP_MULTIPLY,
	KP_SUBTRACT, KP_ADD, KP_COMMA, KP_DOT, KP_ENTER, KP_EQUAL, KP_SEPARATOR,
    KP_LEFT_PAREN, KP_RIGHT_PAREN,

    SHIFT, CONTROL, ALT, SUPER,

	MENU, BACK,

	BACKQUOTE, QUOTE,

	KP_UP, KP_DOWN, KP_LEFT, KP_RIGHT,

	UNDERLINE, SELECT_KEY,

	CANCEL, CLEAR,

	OPEN_BRACKET, CLOSE_BRACKET,

    PLAY, NONAME, FINAL,
    OEM102, OEM1, OEM2, OEM3, OEM4, OEM5, OEM6, OEM7, OEM8,
    LEFT_MENU, RIGHT_MENU,
    SLEEP, SNAPSHOT, INFO,

    XBUTTON1, XBUTTON2, XBUTTON3, XBUTTON4, XBUTTON5, XBUTTON6, XBUTTON7, XBUTTON8, XBUTTON9,
    XBUTTON10, XBUTTON11, XBUTTON12, XBUTTON13, XBUTTON14, XBUTTON15, XBUTTON16,
    XBUTTON_A, XBUTTON_B, XBUTTON_C, XBUTTON_L1, XBUTTON_L2, XBUTTON_MODE,
    XBUTTON_R1, XBUTTON_R2, XBUTTON_SELECT, XBUTTON_START,
    XBUTTON_THUMBL, XBUTTON_THUMBR, XBUTTON_X, XBUTTON_Y, XBUTTON_Z,

    DPAD_CENTER, DPAD_DOWN, DPAD_DOWN_LEFT, DPAD_DOWN_RIGHT, DPAD_LEFT, DPAD_RIGHT, DPAD_UP, DPAD_UP_LEFT, DPAD_UP_RIGHT,

    DVR, EISU, ENDCALL, ENVELOPE, EXPLORER,
    FOCUS, FORWARD, GRAVE, GUIDE, HEADSETHOOK,

    ABNT_C1, ABNT_C2,
    ATTN, CRSEL, EREOF, EXECUTE, EXSEL,
    ICO_CLEAR, ICO_HELP,
    HENKAN, PAIRING,

    APP_SWITCH, ASSIST, AVR_INPUT, AVR_POWER, BOOKMARK, BREAK, CAPTIONS, CAMERA, CALL, CALENDAR,
    BRIGHTNESS_DOWN, BRIGHTNESS_UP,
    CHANNEL_DOWN, CHANNEL_UP,
    CALCULATOR,
    CONTACTS,
    NOTIFICATION,
    COPY, CUT, PASTE,
    SEARCH, SETTINGS,
    SOFT_LEFT, SOFT_RIGHT, SOFT_SLEEP,
    STAR, STB_INPUT, STB_POWER,
    STEM_1, STEM_2, STEM_3, STEM_PRIMARY, SWITCH_CHARSET,
    SYM, SYSRQ, NUM,
    TV, TV_ANTENNA_CABLE, TV_AUDIO_DESCRIPTION, TV_AUDIO_DESCRIPTION_MIX_DOWN, TV_AUDIO_DESCRIPTION_MIX_UP, TV_CONTENTS_MENU,
    TV_DATA_SERVICE, TV_INPUT, TV_INPUT_COMPONENT_1, TV_INPUT_COMPONENT_2,
    TV_INPUT_COMPOSITE_1, TV_INPUT_COMPOSITE_2,
    TV_INPUT_HDMI_1, TV_INPUT_HDMI_2, TV_INPUT_HDMI_3, TV_INPUT_HDMI_4,
    TV_INPUT_VGA_1, TV_MEDIA_CONTEXT_MENU, TV_NETWORK, TV_NUMBER_ENTRY,
    TV_POWER, TV_RADIO_SERVICE, TV_SATELLITE, TV_SATELLITE_BS, TV_SATELLITE_CS,
    TV_SATELLITE_SERVICE, TV_TELETEXT, TV_TERRESTRIAL_ANALOG, TV_TERRESTRIAL_DIGITAL,
    TV_TIMER_PROGRAMMING, TV_ZOOM_MODE,

    VOICE_ASSIST,
    WAKEUP, WINDOW, YEN, ZENKAKU_HANKAKU,
    ZOOM_IN, ZOOM_OUT,

    SYSTEM_NAVIGATION_DOWN, SYSTEM_NAVIGATION_LEFT, SYSTEM_NAVIGATION_RIGHT, SYSTEM_NAVIGATION_UP,
    PICTSYMBOLS, POUND, POWER, PROG_BLUE, PROG_GREEN, PROG_RED, PROG_YELLOW,
    RO,
    OEM_ATTN, OEM_AUTO, OEM_AX, OEM_BACKTAB, OEM_CLEAR, OEM_COMMA, OEM_COPY, OEM_CUSEL, OEM_ENLW, OEM_FINISH,
    OEM_FJ_LOYA, OEM_FJ_MASSHOU, OEM_FJ_ROYA, OEM_FJ_TOUROKU, OEM_JUMP, OEM_MINUS,
    OEM_PA1, OEM_PA2, OEM_PA3,
    OEM_PERIOD, OEM_PLUS, OEM_RESET, OEM_WSCTRL,
    PA1, PACKET, PROCESSKEY, ZOOM, NONE, ACCEPT, APPS,
    BROWSER_BACK, BROWSER_FAVORITES, BROWSER_FORWARD, BROWSER_HOME, BROWSER_REFRESH, BROWSER_SEARCH, BROWSER_STOP,
    CAPITAL, CONVERT, ICO_00, JUNJA, KANA, KANJI, KATAKANA_HIRAGANA, LANGUAGE_SWITCH, MUHENKAN,
    LAUNCH_APP1, LAUNCH_APP2, LAUNCH_MAIL, LAUNCH_MEDIA_SELECT,
    LEFT_BUTTON, MIDDLE_BUTTON,
    MUSIC,
    MEDIA_NEXT_TRACK, MEDIA_PLAY_PAUSE, MEDIA_PREV_TRACK, MEDIA_STOP, MEDIA_PLAY, MEDIA_PAUSE,
    MEDIA_AUDIO_TRACK, MEDIA_CLOSE, MEDIA_EJECT, MEDIA_FAST_FORWARD, MEDIA_RECORD, MEDIA_REWIND,
    MEDIA_SKIP_BACKWARD, MEDIA_SKIP_FORWARD, MEDIA_STEP_BACKWARD, MEDIA_STEP_FORWARD, MEDIA_TOP_MENU,
    MODECHANGE, NEXT, NONCONVERT,

    OEM_FJ_JISHO, PRIOR, RIGHT_BUTTON,
    LAST_CHANNEL, MANNER_MODE,

    NAVIGATE_IN, NAVIGATE_NEXT, NAVIGATE_OUT, NAVIGATE_PREVIOUS,
    HYPHEN,

	UNDEFINED,
	UNKNOWN,

    ;

    val isFunctionKey get() = when (this) {
        F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12,
        F13, F14, F15, F16, F17, F18, F19, F20, F21, F22, F23, F24, F25 -> true
        else -> false
    }

    companion object {
	    val MAX = UNKNOWN.ordinal + 1

        val RETURN = ENTER

		val NUMPAD0 = N0
		val NUMPAD1 = N1
		val NUMPAD2 = N2
		val NUMPAD3 = N3
		val NUMPAD4 = N4
		val NUMPAD5 = N5
		val NUMPAD6 = N6
		val NUMPAD7 = N7
		val NUMPAD8 = N8
		val NUMPAD9 = N9

        @Deprecated("", ReplaceWith("CONTROL", "com.soywiz.korev.Key.CONTROL")) val LEFT_CONTROL get() = CONTROL
        @Deprecated("", ReplaceWith("CONTROL", "com.soywiz.korev.Key.CONTROL")) val RIGHT_CONTROL get() = CONTROL
        @Deprecated("", ReplaceWith("SHIFT", "com.soywiz.korev.Key.SHIFT")) val LEFT_SHIFT get() = SHIFT
        @Deprecated("", ReplaceWith("SHIFT", "com.soywiz.korev.Key.SHIFT")) val RIGHT_SHIFT get() = SHIFT
        @Deprecated("", ReplaceWith("ALT", "com.soywiz.korev.Key.ALT")) val LEFT_ALT get() = ALT
        @Deprecated("", ReplaceWith("ALT", "com.soywiz.korev.Key.ALT")) val RIGHT_ALT get() = ALT
        @Deprecated("", ReplaceWith("SUPER", "com.soywiz.korev.Key.SUPER")) val LEFT_SUPER get() = SUPER
        @Deprecated("", ReplaceWith("SUPER", "com.soywiz.korev.Key.SUPER")) val RIGHT_SUPER get() = SUPER
    }
}
