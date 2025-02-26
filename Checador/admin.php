<?php
include 'conexion.php';

// Eliminar registro
if (isset($_GET['delete'])) {
    $id = $_GET['delete'];
    $pdo->query("DELETE FROM horario WHERE id = $id");
}

// Obtener registros
$stmt = $pdo->query("SELECT id, usuario, fecha, hora_entrada, hora_salida FROM horario ");
$horarios = $stmt->fetchAll();
?>

<table border="1">
    <tr>
        <th>Usuario</th>
        <th>Fecha</th>
        <th>Hora de Entrada</th>
        <th>Hora de Salida</th>
        <th>Acciones</th>
    </tr>
    <?php foreach ($horarios as $horario): ?>
    <tr>
        <td><?= $horario['usuario'] ?></td>
        <td><?= $horario['fecha'] ?></td>
        <td><?= $horario['hora_entrada'] ?></td>
        <td><?= $horario['hora_salida'] ?></td>
        <td>
            <a href="?delete=<?= $horario['id'] ?>">Eliminar</a>
        </td>
    </tr>
    <?php endforeach; ?>
</table>