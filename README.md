
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
F01 | Login |Korisnicima će se omogućiti prijava u sustav. Prilikom prijave će moći izabrati između dvije uloge voditelja i radnika. Svaki zaposleni će se moći prijaviti samo u ulogu za koju ima korisničke podatke | Lovro Pejaković
F02 | Registracija |Da bi pristupili registraciji, korisnici moraju imat posebnu šifru koju imaju samo ovlašteni zaposlenici firme. Korisnicima će se omogućiti kreiranje profila prilikom prvog korištenja aplikacije. Prilikom registracije korisnici mogu izabrati svoju ulogu u aplikaciji. Zaposlenici sa ulogom voditelj imaju omogućene dodatne funkcionalnosti(F05,F09). Ostali zaposlenici imaju ulogu radnik koji|Nikola Parag
F03 | Zaboravljena lozinka | Korisnici će imati mogućnost promjene lozinke ukoliko su je zaboravili. | Matej Ritoša
F04 | Skeniranje QR koda za početak radnog vremena | Korisnici će imati mogućnost skeniranja QR koda za početak mjerenja radnog vremena. Skeniranjem istog QR koda timer koji mjeri radno vrijeme će se zaustaviti i zabilježiti vrijeme rada u bazu podataka | Nikola Parag
F05 | Kreiranje plana rada | Zaposlenici sa ulogom voditelj će moći kreirati planove rada za svakog zaposlenika i dodavati događaje na rasporede rada svih zaposlenih| Nikola Parag
F06 | Plan rada | Korisnici će imati mogućnost prikaza vlastitog rasporeda radnih sati to jest obveza. Prikazan će biti tjedni raspored sa svim aktivnostima u firmi.Svaki zaposleni će imati prikazan svoj dnevni plan i program rada |Matej Ritoša
F07 | Statistika radnih sati | Korisnici će imati uvid u statistiku vlastitih odrađenih radnih sati. Moći će pregledati koliko su sati odradili po mjesecima. Isto tako će moći vidjeti koliko od tih odrađenih radnih sati su prekovremeni sati i koliko tih sati je odrađeno nedeljom/noćna smjena | Lovro Pejaković
F08 | Daily Checklist| Korisnici će imati mogućnost kreiranja i uređivanja liste dnevnih obveza. Korisnici će sami moći kreirati za sebe stavke u checklisti. Moći će ih brisati, uređivati i označiti da su rješene. | Lovro Pejaković
F09 | Generiranje izvještaja radnih sati | Korisnik s ulogom voditelja imat će mogućnost generiranja izvještaja o sveukupno provedenim radnim satima po zaposleniku te će taj generirani izvještaj moći poslati sebi na mail. |Matej Ritoša


## Tehnologije i oprema
Android studio...

## Baza podataka i web server
Trebamo bazu podataka i pristup serveru za PHP skripte.

## .gitignore
Uzmite u obzir da je u mapi Software .gitignore konfiguriran za nekoliko tehnologija, ali samo ako će projekti biti smješteni direktno u mapu Software ali ne i u neku pod mapu. Nakon odabira konačne tehnologije i projekta obavezno dopunite/premjestite gitignore kako bi vaš projekt zadovoljavao kriterije koji su opisani u ReadMe.md dokumentu dostupnom u mapi Software.
