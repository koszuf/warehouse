## Logowanie ##

Logowanie jest zrealizowane przy pomocy [Logback](http://logback.qos.ch/ "Logback"). 

###Poziomy logowania###
W systemie występują następujące poziomy logowania (w kolejnosci od najmniej istotnych do najważniejszych):

- trace
- debug
- info
- warn
- error

### Konfiguracja ##
Konfiguracja modułu znajduje się w pliku `default.logback.xml` który znajduje się w katalogu `src/main/resources/props`

W pliku tym są skonfigurowane dwa appendery (po polskiemu zapisywacze ;), jeden z nich zapisuje logi do pliku (log/warehouse.log) drugi wyswietla je na konsoli.

Następnie skonfigurowane zostało, że z pakietu net.liftweb będą logowane tylko wpisy na poziomie 'warn' i wyżej, natomiast pakiety 'net.pgc.snippet' oraz 'bootstrap.liftweb' będą logowane od poziomu 'info'. Oczywiscie w miare rozbudowy systemu wpisy te trzeba będzie zmienić.

Kolejne wpisy dotyczą tego, że logi na poziomie 'info' i wyżej są zapisywane przy pomocy appendera wyswietlającego je na konsoli. Natomiast appender zapisujący do pliku będzie logował logi na poziomie 'debug' i wyżej.

###Logowanie w systemie###

Aby "włączyć" logowanie w obrębie klasy należy rozszerzyć ją o trait `Logger`. Następnie w obrębie klasy posługujemy się poleceniami info, debug, etc do logowania na odpowiednim poziomie. Przykład:

    class HelloWorld extends Logger {
    	info ("Początek klasy HelloWorld")
		def render = 
			{
			 trace("Początek metody HelloWorld.render")
			 "#time *" #> Helpers.now.toString
 			 trace ("Koniec metody HelloWorld.render")
			}
		info ("Koniec klasy HelloWorld")			 
	}