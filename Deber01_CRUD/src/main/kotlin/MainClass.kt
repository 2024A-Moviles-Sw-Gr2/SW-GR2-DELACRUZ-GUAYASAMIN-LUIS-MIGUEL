import Modelo.GestorCursos
import Modelo.GestorEstudiantes
import Modelo.Curso
import Modelo.Estudiante
import javax.swing.*
import java.awt.*

class MainSwingApp : JFrame("Gestión de Estudiantes y Cursos") {
    private val gestorCursos = GestorCursos()
    private val gestorEstudiantes = GestorEstudiantes()

    private val cursosListModel = DefaultListModel<Curso>()
    private val estudiantesListModel = DefaultListModel<Estudiante>()

    private val cursosList = JList(cursosListModel)
    private val estudiantesList = JList(estudiantesListModel)

    private val nombreCursoField = JTextField(10)
    private val numeroEstudiantesField = JTextField(10)
    private val codigoCursoField = JTextField(10)
    private val activoCursoCheckbox = JCheckBox()
    private val duracionHorasField = JTextField(10)

    private val nombreEstudianteField = JTextField(10)
    private val edadEstudianteField = JTextField(10)
    private val matriculaEstudianteField = JTextField(10)
    private val fechaIngresoEstudianteField = JTextField(10)
    private val promedioEstudianteField = JTextField(10)

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(1000, 600)
        layout = BorderLayout()

        val panelCursos = crearPanelCursos()
        val panelEstudiantes = crearPanelEstudiantes()

        val splitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelCursos, panelEstudiantes)
        add(splitPane)

        actualizarListaCursos()
        actualizarListaEstudiantes()

        isVisible = true
    }

    private fun crearPanelCursos(): JPanel {
        val panelCursos = JPanel(BorderLayout())
        panelCursos.border = BorderFactory.createTitledBorder("Gestión de Cursos")

        val panelDatosCurso = JPanel(GridBagLayout())
        val gbcCurso = GridBagConstraints()
        gbcCurso.gridx = 0
        gbcCurso.gridy = 0
        gbcCurso.anchor = GridBagConstraints.WEST
        panelDatosCurso.add(JLabel("Materia:"), gbcCurso)
        gbcCurso.gridy++
        panelDatosCurso.add(JLabel("Número de Estudiantes:"), gbcCurso)
        gbcCurso.gridy++
        panelDatosCurso.add(JLabel("Código:"), gbcCurso)
        gbcCurso.gridy++
        panelDatosCurso.add(JLabel("Activo:"), gbcCurso)
        gbcCurso.gridy++
        panelDatosCurso.add(JLabel("Duración (Horas):"), gbcCurso)

        gbcCurso.gridx = 1
        gbcCurso.gridy = 0
        gbcCurso.anchor = GridBagConstraints.EAST
        panelDatosCurso.add(nombreCursoField, gbcCurso)
        gbcCurso.gridy++
        panelDatosCurso.add(numeroEstudiantesField, gbcCurso)
        gbcCurso.gridy++
        panelDatosCurso.add(codigoCursoField, gbcCurso)
        gbcCurso.gridy++
        panelDatosCurso.add(activoCursoCheckbox, gbcCurso)
        gbcCurso.gridy++
        panelDatosCurso.add(duracionHorasField, gbcCurso)

        val panelBotonesCurso = JPanel(FlowLayout(FlowLayout.RIGHT))
        val agregarCursoButton = JButton("Agregar Curso")
        val eliminarCursoButton = JButton("Eliminar Curso")
        val actualizarCursoButton = JButton("Actualizar Curso")
        val agregarEstudianteACursoButton = JButton("Agregar Estudiante a Curso")
        panelBotonesCurso.add(agregarCursoButton)
        panelBotonesCurso.add(eliminarCursoButton)
        panelBotonesCurso.add(actualizarCursoButton)
        panelBotonesCurso.add(agregarEstudianteACursoButton)

        agregarCursoButton.addActionListener {
            val nombre = nombreCursoField.text
            val numeroEstudiantes = numeroEstudiantesField.text.toInt()
            val codigo = codigoCursoField.text
            val activo = activoCursoCheckbox.isSelected
            val duracionHoras = duracionHorasField.text.toDouble()

            val curso = Curso(nombre, numeroEstudiantes, codigo, activo, duracionHoras)
            gestorCursos.crearCurso(curso)
            actualizarListaCursos()
        }

        eliminarCursoButton.addActionListener {
            val cursoSeleccionado = cursosList.selectedIndex
            if (cursoSeleccionado != -1) {
                gestorCursos.eliminarCurso(cursoSeleccionado)
                actualizarListaCursos()
            }
        }

        actualizarCursoButton.addActionListener {
            val cursoSeleccionado = cursosList.selectedIndex
            if (cursoSeleccionado != -1) {
                val curso = cursosListModel.get(cursoSeleccionado)
                curso.materia = nombreCursoField.text
                curso.numeroEstudiantes = numeroEstudiantesField.text.toInt()
                curso.codigo = codigoCursoField.text
                curso.activo = activoCursoCheckbox.isSelected
                curso.duracionHoras = duracionHorasField.text.toDouble()
                gestorCursos.actualizarCurso(cursoSeleccionado, curso)
                actualizarListaCursos()
            }
        }

        agregarEstudianteACursoButton.addActionListener {
            val cursoIndex = cursosList.selectedIndex
            val estudianteSeleccionado = estudiantesList.selectedValue
            if (cursoIndex != -1 && estudianteSeleccionado != null) {
                gestorCursos.agregarEstudianteACurso(cursoIndex, estudianteSeleccionado)
                actualizarListaCursos()
            }
        }

        panelCursos.add(panelDatosCurso, BorderLayout.NORTH)
        panelCursos.add(JScrollPane(cursosList), BorderLayout.CENTER)
        panelCursos.add(panelBotonesCurso, BorderLayout.SOUTH)

        return panelCursos
    }

    private fun crearPanelEstudiantes(): JPanel {
        val panelEstudiantes = JPanel(BorderLayout())
        panelEstudiantes.border = BorderFactory.createTitledBorder("Gestión de Estudiantes")
        panelEstudiantes.preferredSize = Dimension(400, height)

        val panelDatosEstudiante = JPanel(GridBagLayout())
        val gbcEstudiante = GridBagConstraints()
        gbcEstudiante.gridx = 0
        gbcEstudiante.gridy = 0
        gbcEstudiante.anchor = GridBagConstraints.WEST
        panelDatosEstudiante.add(JLabel("Nombre:"), gbcEstudiante)
        gbcEstudiante.gridy++
        panelDatosEstudiante.add(JLabel("Edad:"), gbcEstudiante)
        gbcEstudiante.gridy++
        panelDatosEstudiante.add(JLabel("Matrícula:"), gbcEstudiante)
        gbcEstudiante.gridy++
        panelDatosEstudiante.add(JLabel("Fecha de Ingreso:"), gbcEstudiante)
        gbcEstudiante.gridy++
        panelDatosEstudiante.add(JLabel("Promedio:"), gbcEstudiante)

        gbcEstudiante.gridx = 1
        gbcEstudiante.gridy = 0
        gbcEstudiante.anchor = GridBagConstraints.EAST
        panelDatosEstudiante.add(nombreEstudianteField, gbcEstudiante)
        gbcEstudiante.gridy++
        panelDatosEstudiante.add(edadEstudianteField, gbcEstudiante)
        gbcEstudiante.gridy++
        panelDatosEstudiante.add(matriculaEstudianteField, gbcEstudiante)
        gbcEstudiante.gridy++
        panelDatosEstudiante.add(fechaIngresoEstudianteField, gbcEstudiante)
        gbcEstudiante.gridy++
        panelDatosEstudiante.add(promedioEstudianteField, gbcEstudiante)

        val panelBotonesEstudiante = JPanel(FlowLayout(FlowLayout.RIGHT))
        val agregarEstudianteButton = JButton("Agregar Estudiante")
        val eliminarEstudianteButton = JButton("Eliminar Estudiante")
        val actualizarEstudianteButton = JButton("Actualizar Estudiante")
        panelBotonesEstudiante.add(agregarEstudianteButton)
        panelBotonesEstudiante.add(eliminarEstudianteButton)
        panelBotonesEstudiante.add(actualizarEstudianteButton)

        agregarEstudianteButton.addActionListener {
            val nombre = nombreEstudianteField.text
            val edad = edadEstudianteField.text.toInt()
            val matricula = matriculaEstudianteField.text
            val fechaIngreso = fechaIngresoEstudianteField.text
            val promedio = promedioEstudianteField.text.toDouble()

            val estudiante = Estudiante(nombre, edad, matricula, fechaIngreso, promedio)
            gestorEstudiantes.crearEstudiante(estudiante)
            actualizarListaEstudiantes()
        }

        eliminarEstudianteButton.addActionListener {
            val estudianteSeleccionado = estudiantesList.selectedIndex
            if (estudianteSeleccionado != -1) {
                gestorCursos.eliminarEstudianteDeCursos(gestorEstudiantes.leerEstudiantes().get(estudianteSeleccionado).nombre)
                gestorEstudiantes.eliminarEstudiante(estudianteSeleccionado)
            }
            actualizarListaEstudiantes()
            actualizarListaCursos()
        }

        actualizarEstudianteButton.addActionListener {
            val estudianteSeleccionado = estudiantesList.selectedIndex
            if (estudianteSeleccionado != -1) {
                val estudiante = estudiantesListModel.get(estudianteSeleccionado)
                val nombre = estudiante.nombre
                estudiante.nombre = nombreEstudianteField.text
                estudiante.edad = edadEstudianteField.text.toInt()
                estudiante.matricula = matriculaEstudianteField.text
                estudiante.fechaIngreso = fechaIngresoEstudianteField.text
                estudiante.promedio = promedioEstudianteField.text.toDouble()
                gestorCursos.actualizarAlumno(nombre,estudiante)
                gestorEstudiantes.actualizarEstudiante(estudianteSeleccionado, estudiante)
            }
            actualizarListaEstudiantes()
            actualizarListaCursos()
        }

        panelEstudiantes.add(panelDatosEstudiante, BorderLayout.NORTH)
        panelEstudiantes.add(JScrollPane(estudiantesList), BorderLayout.CENTER)
        panelEstudiantes.add(panelBotonesEstudiante, BorderLayout.SOUTH)

        return panelEstudiantes
    }

    private fun actualizarListaCursos() {
        cursosListModel.clear()
        gestorCursos.leerCursos().forEach {
            cursosListModel.addElement(it)
        }
    }

    private fun actualizarListaEstudiantes() {
        estudiantesListModel.clear()
        gestorEstudiantes.leerEstudiantes().forEach {
            estudiantesListModel.addElement(it)
        }
    }
}

fun main() {
    SwingUtilities.invokeLater {
        MainSwingApp()
    }
}

