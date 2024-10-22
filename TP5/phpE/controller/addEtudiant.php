<?php
include_once '../racine.php';
include_once RACINE.'/service/EtudiantService.php';
extract($_POST);
if (isset($_FILES['image'])) {
    $image = $_FILES['image']['tmp_name'];
    $imgContent = addslashes(file_get_contents($image));
}
$es = new EtudiantService();
$es->createForWeb(new Etudiant(1, $nom, $prenom, $ville, $sexe,$imgContent));
header("location:../index.php");
?>