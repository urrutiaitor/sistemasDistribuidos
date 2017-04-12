/*
 *
 * Aitor Urrutia Zubikarai
 * 2016/03/01
 * Socket buruzko kontrol puntua
 * 3.en ariketa
 *
 */

3 aplikazio daude:
-> CalculiClient (Klasean egindakoa)
-> CalculiServer (Klasean egindakoa)
-> Interceptor (Nik eginda)

Interceptor aplikazioari parametro bezala <host> <puerto con cliente> <puerto con servidor> bidali besar zaio. Beste aplikazio biak lotzeko 2 portu desberdin erabiltzen dira.
Horero gain, Interceptor barruan transmititutako byte guztiak pantailan bistaratzen dira.
Azkenik, Interceptor klaseak bertatik pasatzen den guztia fitxategietan gordetzen du.
