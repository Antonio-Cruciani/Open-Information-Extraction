<!DOCTYPE html>
<html>
<style>
body
{
  background-image:url('./zoomed_out1.png');
  background-attachment:fixed;
  
}
input[type=submit] {
  width: 10%;
  background-color: #4CAF50;
  color: white;
  padding: 14px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
input[type=text], select {
  width: 80%;
  
  padding: 12px 20px;
  margin: 8px 8px;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
 }

label {
    display: block;
    text-align: center;
    line-height: 150%;
    font-size: 1.5em;
}
.container {
  max-width: 500px;
  width: 110%;
  margin: 0 auto;
  position: relative;
background: rgba(716, 175, 80, 0.5);


}
#contact {
  background: #F9F9F9;
  padding: 25px;
  margin: 150px 0;
  box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
}


.center {
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 50%;
}
</style>
<head>
<meta charset="ISO-8859-1">
<title>KbRacing End-User</title>
</head>
<body>
<div class="container" >
<!-- <h1 align="center">Da qui puoi avere un riepilogo delle informazioni</h1> -->
<img class="center" src="./logo.png" style="width:256px;height:256px;">
<form action="getQuery">
	
 <label for="fname">Inserisci il Soggetto di cui cerchi informazioni (Hamilton, ?, ?) </label>
<input type="text" placeholder="es: Hamilton oppure Niki+Lauda"name="sbj"/><br>
<div style="text-align:center">  
	<input type="SUBMIT" style="width: 300px; margin: 0 auto;" value="Cerca" />
	</div>  
	
</form>

<form action="getQuery3">
	
 <label for="fname2">Inserisci Relazione (?, be, ?) </label>
<input type="text" placeholder="es: be"name="pred"/><br>


<div style="text-align:center">
  
	<input type="SUBMIT" style="width: 300px; margin: 0 auto;" value="Cerca" />
	</div>  
	
</form>

<form action="getQuery2">
	
 <label for="fname1">Inserisci Relazione e oggettto (?, be, driver) </label>
<input type="text" placeholder="es: be"name="pred"/><br>
<input type="text" placeholder="es: driver"name="obj"/><br>

<div style="text-align:center">
  
	<input type="SUBMIT" style="width: 300px; margin: 0 auto;" value="Cerca" />
	</div>  
	
</form>
</div>
</body>
</html>