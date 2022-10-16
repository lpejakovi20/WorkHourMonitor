
# Sustav za praćenje radnih sati zaposlenika

## Projektni tim

Ime i prezime | E-mail adresa (FOI) | JMBAG | Github korisničko ime | Seminarska grupa
------------  | ------------------- | ----- | --------------------- | ----------------
Nikola Parag | nparag20@student.foi.hr | 0035217959 | nparag20 | G02
Lovro Pejaković| lpejakovi20@student.foi.hr | 0016150223 | lpejakovi20 | G02
Matej Ritoša|  mritosa20@student.foi.hr | 0016150816 | mritosa | G02

## Opis domene
Često dolazi do problema ispravnog zabilježavanja radnih sati zaposlenika. Kada šefa nema na poslu on ne može lako pratiti koji je zaposlenik koliko radio. Ponekad je potrebno ostati malo duže na poslu i te vrijedne minute nitko ne prati. Kako zaposlenici nebi bili zakinuti za radne sate za njih smo odlućili kreirati aplikaciju s kojom će oni ,ali i šefovi moći pratiti njihove radne sate.

## Specifikacija projekta
Pristup aplikaciji će imati radnici tvrtke i njihovi voditelji(šef, menadžer,direktor). Bit će podjeljeni u dvije uloge u aplikaciji i prema tome će imati pristup različitim funkcionalnostima aplikacije. Svaki novi zaposlenik tvrtke će samo morati registrirati novi korisnički račun i odmah će moći početi bilježiti svoje radne sate. Ako neki korisnik zaboravi svoju lozinku imat će opciju da ju promjeni. Svi korisnici će moći kada stignu na posao skenirati QR kod i od tada će im se bilježiti njihovo radno vrijeme. Naravno svi zaposlenici će moći pregledati svoje prošle radne sate.Voditelji će moći generirati izvještaje o radnim satima svih zaposlenika i poslati ih mailom za bolji pregled na računalo. Korisnici će imati zaseban kalendar gdje će moći vidjeti svoje tjedno radno vrijeme. Na kalendare će voditelji tvrke moći postaviti nove događaje ili sastanke. Jedna korisna funkcija kojoj će korisnici imati pristup je personalna dnevna ček lista i promjena nekih opcija u svom profilu.

Oznaka | Naziv | Kratki opis | Odgovorni član tima
------ | ----- | ----------- | -------------------
F01 | Login |Korisnicima će se omogućiti prijava u sustav. | Lovro Pejaković
F02 | Registracija | Korisnicima će se omogućiti kreiranje profila prilikom prvog korištenja aplikacije.|Nikola Parag
F03 | Zaboravljena lozinka | Korisnici će imati mogućnost promjene lozinke ukoliko su je zaboravili. | Matej Ritoša
F04 | Skeniranje QR koda za početak radnog vremena | Korisnici će imati mogućnost skeniranja QR koda za početak mjerenja radnog vremena. | Nikola Parag
F05 | Raspored rada | Korisnici će imati mogućnost prikaza vlastitog rasporeda radnih sati to jest obveza. |Matej Ritoša
F06 | Statistika radnih sati | Korisnici će imati uvid u statistiku vlastitih provedenih radnih sati. | Lovro Pejaković
F07 | Korisnički profil | Korisnici će imati mogućnost mjenjati opcije aplikacije| Nikola Parag
F08 | Daily Checklist| Korisnici će imati mogućnost kreiranja i uređivanja liste dnevnih obveza. | Lovro Pejaković
F09 | Generiranje izvještaja radnih sati | Korisnik s ulogom voditelja imat će mogućnost generiranja izvještaja o sveukupno provedenim radnim satima po zaposleniku te će taj generirani izvještaj moći poslati sebi na mail. |Matej Ritoša


## Tehnologije i oprema


## Baza podataka i web server
Trebamo bazu podataka i pristup serveru za PHP skripte.

## .gitignore
Uzmite u obzir da je u mapi Software .gitignore konfiguriran za nekoliko tehnologija, ali samo ako će projekti biti smješteni direktno u mapu Software ali ne i u neku pod mapu. Nakon odabira konačne tehnologije i projekta obavezno dopunite/premjestite gitignore kako bi vaš projekt zadovoljavao kriterije koji su opisani u ReadMe.md dokumentu dostupnom u mapi Software.
