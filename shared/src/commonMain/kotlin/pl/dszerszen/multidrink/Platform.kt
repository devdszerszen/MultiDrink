package pl.dszerszen.multidrink

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform