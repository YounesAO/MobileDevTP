<?php
if($_SERVER["REQUEST_METHOD"] == "POST"){
    include_once  '../racine.php';
    include_once  RACINE.'/service/EtudiantService.php';
update();
}

function update(){
extract($_POST);
$es = new EtudiantService();
$es->update(new Etudiant($id, $nom, $prenom, $ville, $sexe,base64_decode($image)));
header('Content-type: application/json');
echo json_encode($es->findAllApi());
}
?>