#!/bin/bash
javac Client.java
java Client < Cinput.txt > Cur_User_out.txt
java Client < Dinput.txt > Date_out.txt
java Client < Minput.txt > Mem_out.txt
java Client < Ninput.txt > Net_out.txt
java Client < Rinput.txt > Run_out.txt
java Client < Uinput.txt > UpT_out.txt
