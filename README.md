##Warehouse system##

###1. Cel###
System *Warehouse* zapewnia prostą obsługę małego magazynu. Został on utworzony w celu uproszczenia koordynacji przepływów magazynowych oraz dokumentowania ich.
###2. Narzędzia###
System został zaprojektowany na platformę web. Może być uruchamiany w dowolnym kontenerze serwletów, np. tomcat. Platformą jest Lift/Scala 
###3. Procesy###
1. **Przyjęcie na magazyn** - na razie nieobsługiwane
2. **Wydanie z magazynu** - Operator rejestruje wydanie produktów z magazynu - każdemu wydaniu przypisane są następujące dane: Nagłówek (Data, operator, osoba której wydano, firma, nr dokumentu wydania, nr zamówienia) oraz pozycje (produkt, ilosć). Podczas rejestracji nagłówka system sprawdza, czy na nr zamówienia zostało już cos wydane. Jeżeli tak - operator zostanie o tym poinformowany
3. **Reprint WZ** - operator ma możliwosć ponownego wydrukowania WZ-ki
4. **Utworzenie WZ** - operator ma możliwosć rejestracji WZ-ki nie przypisanej do żadnego wydania, ręcznie wypełniając tresć dokumentu. System przechowuje takie WZ-ki i umożliwia ich wydruk
5. **Drukowanie raportów** - system pozwala na drukowanie raportów zgodnie z załączoną listą
6. **Logowanie do systemu** - system przechowuje własną listę operatorów (użytkowników) umożliwiając własną autoryzację lub poprzez protokół LDAP
###4. Dokumenty
1. Wydanie z magazynu
2. Przyjęcie na magazyn
###5. Specyfikacja danych
System korzysta z bazy danych zdefiniowanej w pliku konfiguracyjnym
1. Produkty
2. Operatorzy
3. Wydania
4. Firmy

###6. Nice to have
1. Ładny wygląd
2. Obsługa skanerów kodów kreskowych