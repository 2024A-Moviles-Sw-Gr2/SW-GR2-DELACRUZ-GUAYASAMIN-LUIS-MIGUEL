import java.util.*

fun main(){
    println("Hola Mundo")
    //Inmutables no se pueden reasignar
    val inmutable:String = "Luis"
    // inmutable = "" error
    //Mutables
    var mutable: String = "Miguel"
    mutable = "Luis" // ok
    // val + var

    // duck typing
    var ejemploDeVariable = "Luis Miguel"
    var edadEjemplo: Int = 12

    ejemploDeVariable.trim()
    // ejemploDeVariable = edadEjemplo error

    val nombre:String = "Luis"
    val sueldo:Double = 1.3
    val estadoCivil:Char = 's'
    val mayorEdad:Boolean = true
    //clase
    val fechaNacimiento: Date = Date()


    // When (Switch)
    val  estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        ("S") -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }



    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No" //if else chiquito

    // uso de funciones
    calcularSueldo(10.00)
    calcularSueldo(10.00, 12.00, 25.00)
    //Named parameters
    //calcularSueldo(sueldo, tasa, bonoEspecial)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(sueldo= 10.00, bonoEspecial = 20.00, tasa = 14.00)

    // uso de clase
    val sumaUno = Suma(1,1) // Suma(1,1) en KOTLIN no hay "new"
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1,null)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)


    // arreglos
    // arreglos estaticos
    val arregloEstatico:Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)
    // arreglos dinamicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1,2,3,4,5,6,7,8,9,10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    // operadores
    // FOR EACH = > UNIT
    // iterar areglos
    val respuestaForEach: Unit = arregloDinamico.forEach{ valorActual : Int ->
        println("Valor Actual: ${valorActual}")
    }
    // "it" {en ingless "eso"} significa el elemento iteado
    arregloDinamico.forEach { println("Valor Actual (it): ${it}") }

    // Map -> muta(modifica o cambia)el areglo
    // 1) Envia el nuevo valor de la iteración
    // 2) Nos devuelve un nuevo aeeglo con valores de las iteraciones

    val respuestaMap: List<Double> = arregloDinamico.map { valorActual: Int ->
        return@map valorActual.toDouble() +100.00
    }
    println(respuestaMap)
    val respuestaMap2= arregloDinamico.map { it +15}
    println(respuestaMap2)

    // Filter -> Filtra el arreglo
    // 1) Devuelve una expresión (true or false)
    // 2) Nuevo arreglo filtrado
    val respuestaFilter: List<Int> = arregloDinamico.filter { valorActual: Int ->
        //Expresión o Condición
        val mayoresACinco :Boolean = valorActual > 5
        return@filter mayoresACinco
    }
    val respuestaFilterDos = arregloDinamico.filter { it  <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    //Operadores lógicos
    //OR AND
    //OR -> ANY (ALGUNO CUMPLE?)
    // AND -> ALL (TODOS CUMPLEN?)
    val respuestaAny:Boolean = arregloDinamico.any{valorActual:Int ->
        return@any (valorActual > 5)
    }
    println(respuestaAny)  //true

    val respuestaAll:Boolean = arregloDinamico.all{valorActual:Int ->
        return@all (valorActual > 5)
    }
    println(respuestaAll)  //False


    // Reduce -> valor acumulado
    // valor acumulado = 0 (Siempre empieza en 0 en Kotlin)
    // {1,2,3,4,5} -> Acumular "sumar" estos valores del arreglo
    // valorIteracion1 = valorEmpiezo + 1 = 0+1 = 1 -> Iteracion1
    // valorIteracion2 = valorIteracion1 +2 = 1+2 = 3 -> Iteracion2
    // valorIteracion3 = valorIteracion2 +3 = 3+3 = 6 -> Iteracion3
    // valorIteracion4 = valorIteracion3 +4 = 6+4 = 10 -> Iteracion4
    // valorIteracion5 = valorIteracion4 +5 = 10+5 = 15 -> Iteracion5

    val respuestaReduce: Int= arregloDinamico.reduce{ acumulado:Int , valorActual:Int ->
        return@reduce (acumulado + valorActual)
    }
    println(respuestaReduce)




}


//void --> unit
 fun imprimirNombre(nombre:String){
     println("Nombre: ${nombre}")
 }

//
fun calcularSueldo(
      sueldo:Double // requerida
    , tasa:Double = 12.5 // (opcional)defecto
    , bonoEspecial:Double? = null // opcional nullable
    // varaible? -> es nullable (en algún momento es nulo)
    ):Double{
    // Int -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) * bonoEspecial
    }
}
abstract class Numeros( //constructor primario
// caso 1 paramatro normal
// uno:Int ,  parametro sin modificador de acceso

//caso 2
// propiedad de instancia .uno (se convierte en propiedad de la clase)
// private var uno:Int , propiedad instancia 1
protected val numeroUno:Int, //instancia numeroUno
protected val numeroDos:Int  //instancia numeroDos

//parametroInutil:String //Parametro
){
    init{ // bloqueo constructor primario (opcional)
        this.numeroUno
        this.numeroDos
        // this.parametroInutil , error no existe no es parte de las propiedades de la clase
        println("Inicializando")
    }

}
class Suma(//
   parametroUno:Int, //instancia numeroUno
   parametroDos:Int  //instancia numeroDos

//
): Numeros(// clase papá, numeros (extendiendo))}
    parametroUno,
    parametroDos
) {

    public val soyPublicoExplicito:String = "Explicito"
    val soyPublicoImplicito:String = "Implicito"
    init {
        //this.parametroUno error no existe
        this.numeroUno
        this.numeroDos
        numeroUno // this. opcional (propiedad metodos )
        numeroDos // this. opcional (propiedad metodos )
        this.soyPublicoExplicito
        soyPublicoImplicito // this. opcional (propiedad metodos )
    }

    constructor( //constructor secundario
        uno: Int?,
        dos:Int
    ):this(
        if(uno==null) 0 else uno,
        dos
    )

    constructor( // constructor tercero
        uno:Int,
        dos:Int?
    ):this(
        uno,
        if(dos== null) 0 else dos,
    )
    constructor(//constructor cuarto
    uno:Int?,
    dos:Int?
    ):this(
        if(uno==null) 0 else uno,
        if(dos== null) 0 else dos
    )


    // public fun sumar()Int{ (Modificar "public" es OPCIONAL
    fun sumar():Int{
        val total = numeroUno + numeroDos
        // Suma.agregarHistorial(total) ("Suma." o "NombreClase." es OPCIONAL)
        agregarHistorial(total)
        return total
    }
    companion object{ // Comparte entre todas las instancias, similar al Static
        // funciones y variables
        val pi = 3.14
        fun elevarAlCuadrado(num:Int):Int{
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma:Int){
            historialSumas.add(valorTotalSuma)
        }
    }
}





