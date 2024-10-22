<!DOCTYPE html>
<?php
include_once './racine.php';
?>
<html>

<head>
    <meta charset="UTF-8">
    <title>Gestion Etudiant</title>
    <link rel="stylesheet" href="style.css">
</head>

<body>
    <form method="POST" action="controller/addEtudiant.php" enctype="multipart/form-data">
        <fieldset>
            <legend>Ajouter un nouveau étudiant</legend>
            <table BORDER="0">
                <tr>
                    <td>Nom : </td>
                    <td><input type="text" name="nom" value="" /></td>
                </tr>
                <tr>
                    <td>Prenom :</td>
                    <td><input type="text" name="prenom" value="" /></td>
                </tr>
                <tr>
                    <td>Ville</td>
                    <td>
                        <select name="ville">
                            <option value="Marrakech">Marrakech</option>
                            <option value="Tinghir">Tinghir</option>
                            <option value="Rabat">Rabat</option>
                            <option value="Agadir">Agadir</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Sexe </td>
                    <td>
                        Homme<input type="radio" name="sexe" value="homme" />
                        Femme<input type="radio" name="sexe" value="femme" />
                    </td>
                </tr>
                <tr><span>Image:</span>
                <input type="file" name="image" id="image" required>

                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="submit" value="Envoyer" />
                        <input type="reset" value="Effacer" />
                    </td>
                </tr>
                
            </table>
        </fieldset>
    </form>
    <table BORDER="1">
        <thead>
            <tr>
                <th>Img</th>
                <th>ID</th>
                <th>Nom</th>
                <th>Prenom</th>
                <th>Ville</th>
                <th>Sexe</th>
                <th>Supprimer</th>
                <th>Modifier</th>
            </tr>
        </thead>
        <tbody>
            <?php
            include_once RACINE.'/service/EtudiantService.php';
            $es = new EtudiantService();
            foreach ($es->findAll() as $e) {
            ?>
                <tr>
                    <td><?php echo '<img width="50px" src="data:image/png;base64,' .base64_encode($e->getImage()) . '" alt="Image"> '?></td>
                    <td><?php echo $e->getId(); ?></td>
                    <td><?php echo $e->getNom(); ?></td>
                    <td><?php echo $e->getPrenom(); ?></td>
                    <td><?php echo $e->getVille(); ?></td>
                    <td><?php echo $e->getSexe(); ?></td>
                    <td>
                        <a href="controller/deleteEtudiant.php?id=<?php echo $e->getId(); ?>">
                            Supprimer</a>
                    </td>
                    <td><a href="updateEtudiant.php">Modifier</a></td>
                </tr>
            <?php } ?>
        </tbody>
    </table>
</body>

</html>
