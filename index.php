<?php
exec("gpio mode 0 out");
exec("gpio mode 2 out");
exec("gpio mode 3 out");
if (isset($_GET['led1'])) {	
	if($_GET['led1'] == 1) {
		exec("gpio write 0 1");// pin 0 in wiring pi is gpio 17
		echo "Led1 Acik";
	} else {
		exec("gpio write 0 0");
		echo "Led1 Kapali";
	}
}
if(isset($_GET['led2'])) {
	if($_GET['led2'] == 1) {
		exec("gpio write 2 1");// pin 2 in wiring pi is gpio 27
		echo "Led2 Acik";
	} else {
		exec("gpio write 2 0");
		echo "Led2 Kapali";
	}
}
if(isset($_GET['led3'])) {
	if($_GET['led3'] == 1) {
		exec("gpio write 3 1");// pin 3 in wiring pi is gpio 22
		echo "Led3 Acik";
	} else {
		exec("gpio write 3 0");
		echo "Led3 Kapali";
	}
}
?>
