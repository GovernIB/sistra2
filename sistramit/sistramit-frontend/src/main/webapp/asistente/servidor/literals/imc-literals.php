<?
header('Content-Type: text/javascript');

$idioma = (isset($_GET['idioma'])) ? $_GET['idioma'] : "";

if ($idioma == "ca"){
  echo file_get_contents('imc-literals-ca.js');
} else {
  echo file_get_contents('imc-literals-es.js');
}
?>