class Tiempo(var horas: Int, var minutos: Int, var segundos: Int) {

    constructor(horas: Int, minutos: Int) : this(horas, minutos, segundos = 0)
    constructor(horas: Int) : this(horas, minutos = 0, segundos = 0)

    init {
        validar()
        ajustar()
    }

    companion object {
        fun pedirTiempo(): Tiempo {
            val hora = pedirHora()
            val minuto = pedirMinuto()
            val segundo = pedirSegundo()
            return Tiempo(hora, minuto, segundo)
        }

        private fun pedirHora(): Int {
            var hora: Int
            while (true) {
                print("Introduce la hora (0-23): ")
                hora = readLine()?.toIntOrNull() ?: continue
                if (hora in 0..23) break
                println("**ERROR** La hora debe estar entre 0 y 23.")
            }
            return hora
        }

        private fun pedirMinuto(): Int {
            var minuto: Int
            while (true) {
                print("Introduce los minutos (0-59): ")
                minuto = readLine()?.toIntOrNull() ?: continue
                if (minuto in 0..59) break
                println("**ERROR** Los minutos deben estar entre 0 y 59.")
            }
            return minuto
        }

        private fun pedirSegundo(): Int {
            var segundo: Int
            while (true) {
                print("Introduce los segundos (0-59): ")
                segundo = readLine()?.toIntOrNull() ?: continue
                if (segundo in 0..59) break
                println("**ERROR** Los segundos deben estar entre 0 y 59.")
            }
            return segundo
        }
    }

    private fun validar() {
        require(minutos in 0..59) { "Los minutos deben estar entre 0 y 59" }
        require(segundos in 0..59) { "Los segundos deben estar entre 0 y 59" }
        validarHora()
    }

    private fun validarHora() {
        require(horas in 0..23) { "La hora debe ser un valor entre 00 y 23" }
    }

    private fun ajustar() {
        val (segundosAjustados, minutosIncremento) = ajustarUnidad(segundos)
        segundos = segundosAjustados
        minutos += minutosIncremento

        val (minutosAjustados, horasIncremento) = ajustarUnidad(minutos)
        minutos = minutosAjustados
        horas += horasIncremento

        validarHora()
    }

    private fun ajustarUnidad(valor: Int): Pair<Int, Int> {
        val incremento = valor / 60
        val ajustado = valor % 60
        return Pair(ajustado, incremento)
    }

    fun incrementar(t: Tiempo): Boolean {
        var seg = segundos + t.segundos
        var min = minutos + t.minutos
        var h = horas + t.horas

        while (seg >= 60) {
            min++
            seg -= 60
        }
        while (min >= 60) {
            h++
            min -= 60
        }
        if (h > 23) {
            println("**ERROR** El tiempo incrementado superaría las 23:59:59")
            return false
        } else {
            horas = h
            minutos = min
            segundos = seg
            println(this)
            return true
        }
    }

    fun decrementar(t: Tiempo): Boolean {
        var seg = segundos - t.segundos
        var min = minutos - t.minutos
        var h = horas - t.horas

        while (seg < 0) {
            min--
            seg += 60
        }
        while (min < 0) {
            h--
            min += 60
        }
        if (h < 0) {
            println("**ERROR** El tiempo decrementado sería inferior a 00:00:00")
            return false
        } else {
            horas = h
            minutos = min
            segundos = seg
            println(this)
            return true
        }
    }

    fun comparar(t: Tiempo): Int {
        return when {
            horas < t.horas -> -1
            horas > t.horas -> 1
            minutos < t.minutos -> -1
            minutos > t.minutos -> 1
            segundos < t.segundos -> -1
            segundos > t.segundos -> 1
            else -> 0
        }
    }

    fun copiar(): Tiempo {
        return Tiempo(horas, minutos, segundos)
    }

    fun copiar(t: Tiempo) {
        horas = t.horas
        minutos = t.minutos
        segundos = t.segundos
        println("Copiado: $this")
    }

    fun sumar(t: Tiempo): Tiempo? {
        var seg = segundos + t.segundos
        var min = minutos + t.minutos
        var h = horas + t.horas

        while (seg >= 60) {
            min++
            seg -= 60
        }
        while (min >= 60) {
            h++
            min -= 60
        }
        if (h > 23) {
            println("**ERROR** El tiempo sumado superaría las 23:59:59")
            return null
        } else {
            return Tiempo(h, min, seg)
        }
    }

    fun restar(t: Tiempo): Tiempo? {
        var seg = segundos - t.segundos
        var min = minutos - t.minutos
        var h = horas - t.horas

        while (seg < 0) {
            min--
            seg += 60
        }
        while (min < 0) {
            h--
            min += 60
        }
        if (h < 0) {
            println("**ERROR** El tiempo restado sería inferior a 00:00:00")
            return null
        } else {
            return Tiempo(h, min, seg)
        }
    }

    fun esMayorQue(t: Tiempo): Boolean {
        return comparar(t) == 1
    }

    fun esMenorQue(t: Tiempo): Boolean {
        return comparar(t) == -1
    }

    override fun toString(): String {
        return "${"%02d".format(horas)}h, ${"%02d".format(minutos)}m, ${"%02d".format(segundos)}s."
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Tiempo) { return false }
        if (this === other) { return true }
        return (this.horas == other.horas && this.minutos == other.minutos && this.segundos == other.segundos)
    }
}

fun main() {
    val tiempo = Tiempo.pedirTiempo()
    println(tiempo)

    tiempo.incrementar(Tiempo.pedirTiempo())
    tiempo.decrementar(Tiempo.pedirTiempo())

    println("Comparación: ${tiempo.comparar(Tiempo.pedirTiempo())}")

    val tiempoDos = tiempo.copiar()
    tiempo.copiar(Tiempo.pedirTiempo())

    val tiempoTres = tiempo.sumar(Tiempo.pedirTiempo())
    val tiempoCuatro = tiempo.restar(Tiempo.pedirTiempo())

    if (tiempo.esMayorQue(Tiempo.pedirTiempo())) {
        println("Es mayor que")
    } else if (tiempo.esMenorQue(Tiempo.pedirTiempo())) {
        println("Es menor")
    }
}
