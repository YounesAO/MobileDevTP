<?php
include_once RACINE . '/classes/Etudiant.php';
include_once RACINE . '/connexion/Connexion.php';
include_once RACINE . '/dao/IDao.php';
class EtudiantService implements IDao
{
    private $connexion;
    function __construct()
    {
        $this->connexion = new Connexion();
    }
    public function create($o)
    {
        $query = "INSERT INTO Etudiant (`nom`, `prenom`, `ville`, `sexe`, `image`) VALUES (?, ?, ?, ?, ?)";
        $req = $this->connexion->getConnexion()->prepare($query);

        // Execute the prepared statement with an array of values
        $req->execute([$o->getNom(), $o->getPrenom(), $o->getVille(), $o->getSexe(), $o->getImage()]) or die('Erreur SQL');

    }
    public function createForWeb($o)
    {
        $query = "INSERT INTO Etudiant (`id`,`nom`, `prenom`, `ville`, `sexe`, `image`) "
                . "VALUES (NULL, '" . $o->getNom() . "', '" . $o->getPrenom() . "',
        '" . $o->getVille() . "', '" . $o->getSexe() . "', '" . $o->getImage() . "');";
            $req = $this->connexion->getConnexion()->prepare($query);
            $req->execute() or die('Erreur SQL');
    }
    public function delete($o)
    {
        $query = "delete from Etudiant where id = " . $o->getId();
        $req = $this->connexion->getConnexion()->prepare($query);
        $req->execute() or die('Erreur SQL');
    }
    public function findAll()
    {
        $etds = array();
        $query = "select * from Etudiant";
        $req = $this->connexion->getConnexion()->prepare($query);
        $req->execute();
        while ($e = $req->fetch(PDO::FETCH_OBJ)) {
            $etds[] = new Etudiant($e->id, $e->nom, $e->prenom, $e->ville, $e->sexe,$e->image);
        }
        return $etds;
    }
    public function findById($id)
    {
        $query = "select * from Etudiant where id = " . $id;
        $req = $this->connexion->getConnexion()->prepare($query);
        $req->execute();
        if ($e = $req->fetch(PDO::FETCH_OBJ)) {
            $etd = new Etudiant($e->id, $e->nom, $e->prenom, $e->ville, $e->sexe,$e->image);
        }
        return $etd;
    }
    public function update($o)
    {
        $query = "UPDATE `etudiant` SET `nom` = ?, `prenom` = ?, `ville` = ?, `sexe` = ?, `image` = ? WHERE `etudiant`.`id` = ?";
        $req = $this->connexion->getConnexion()->prepare($query);

        // Bind the values to the prepared statement
        $req->execute([$o->getNom(), $o->getPrenom(), $o->getVille(), $o->getSexe(), $o->getImage(), $o->getId()]) or die('Erreur SQL');
    }

    public function findAllApi()
    {
        $query = "select `id`, `nom`, `prenom`, `ville`, `sexe`, `image` from Etudiant";
        $req = $this->connexion->getConnexion()->prepare($query);
        $req->execute();
        $students = $req->fetchAll(PDO::FETCH_ASSOC);

        // Loop through each student and encode the 'image' field as Base64
        foreach ($students as &$student) {
            // Encode the image in Base64 format, assuming the 'image' field is a BLOB
            if (!empty($student['image'])) {
                $student['image'] = base64_encode($student['image']);
            }
        }
    
        return $students;
    }
}
