/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avventur.game;

/**
 *
 * @author W-book
 */
import avventur.load.LoadGame;
import DBMSconnection.Connectiondb;
import avventur.AssignDescription;
import avventur.Engine;
import avventur.GameDescription;
import avventur.parser.ParserOutput;
import avventur.type.ObjectGame;
import avventur.type.Command;
import avventur.type.CommandType;
import avventur.type.Room;
import java.io.PrintStream;
import java.util.Iterator;
import avventur.type.BottigliaWhisky;
import avventur.type.FlashLight;
import avventur.type.LetteraAddio;
import avventur.type.Pg;
import avventur.type.Siringa;
import avventur.type.Zombie;
import interfaccia.DBMSInterface;
import interfaccia.LoadInterface;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
import static java.lang.System.out;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KillZombie extends GameDescription {

    Room[] arrayRoom = new Room[15];

    public void init(int health, int position, List<Zombie> zombie, List<ObjectGame> oggetto) throws Exception {
        //comandi
        Command leggi = new Command(CommandType.READ, "leggi");
        leggi.setAlias(new String[]{"Leggi", "leggi indovinello"});
        getCommands().add(leggi);
        Command scappa = new Command(CommandType.ESCAPE, "scappa");
        scappa.setAlias(new String[]{"scappa", "fuggi", "nasconditi", "SCAPPA","torna indietro"});
        getCommands().add(scappa);
        Command shoot = new Command(CommandType.SHOOT, "spara");
        shoot.setAlias(new String[]{"killalo", "pushalo", "ammazzalo", "colpiscilo", "spara"});
        getCommands().add(shoot);
        Command nord = new Command(CommandType.NORD, "nord");
        nord.setAlias(new String[]{"n", "N", "Nord", "NORD"});
        getCommands().add(nord);
        Command iventory = new Command(CommandType.INVENTORY, "inventario");
        iventory.setAlias(new String[]{"inv", "i", "I"});
        getCommands().add(iventory);
        Command sud = new Command(CommandType.SOUTH, "sud");
        sud.setAlias(new String[]{"s", "S", "Sud", "SUD"});
        getCommands().add(sud);
        Command sali = new Command(CommandType.GO_UP, "sali");
        sali.setAlias(new String[]{"sali sull'elicottero", "sali su", "arrampicati", "vai su"});
        getCommands().add(sali);
        Command est = new Command(CommandType.EAST, "est");
        est.setAlias(new String[]{"e", "E", "Est", "EST"});
        getCommands().add(est);
        Command ovest = new Command(CommandType.WEST, "ovest");
        ovest.setAlias(new String[]{"o", "O", "Ovest", "OVEST"});
        getCommands().add(ovest);
        Command end = new Command(CommandType.END, "end");
        end.setAlias(new String[]{"end", "fine", "esci", "muori", "ammazzati", "ucciditi", "suicidati", "exit"});
        getCommands().add(end);
        Command look = new Command(CommandType.LOOK_AT, "osserva");
        look.setAlias(new String[]{"guarda", "vedi", "trova", "cerca", "descrivi"});
        getCommands().add(look);
        Command pickup = new Command(CommandType.PICK_UP, "raccogli");
        pickup.setAlias(new String[]{"prendi"});
        getCommands().add(pickup);
        Command drawBlood = new Command(CommandType.DRAW_BLOOD, "preleva");
        drawBlood.setAlias(new String[]{"prendi sangue", "preleva sangue", "raccogli sangue"});
        getCommands().add(drawBlood);
        Command open = new Command(CommandType.OPEN, "apri");
        open.setAlias(new String[]{"sfonda"});
        getCommands().add(open);
        Command smoke = new Command(CommandType.SMOKE, "fuma");
        smoke.setAlias(new String[]{"fuma", "smoke", "fuma sigaretta"});
        getCommands().add(smoke);
        Command go = new Command(CommandType.GO, "go");
        go.setAlias(new String[]{"entra"});
        getCommands().add(go);
        Command save = new Command(CommandType.SAVE, "salva");
        save.setAlias(new String[]{"memorizza partita", "salva gioco"});
        getCommands().add(save);
        Command leave = new Command(CommandType.LEAVE, "leave");
        leave.setAlias(new String[]{"lascia", "getta", "abbandona"});
        getCommands().add(leave);
        Command usa = new Command(CommandType.USE, "usa");
        usa.setAlias(new String[]{"usa", "utilizza", "sfrutta"});
        getCommands().add(usa);
        Command launch = new Command(CommandType.LAUNCH, "lanciati");
        launch.setAlias(new String[]{"lancia", "gettati", "paracadutati","buttati"});
        getCommands().add(launch);
        Command accendi = new Command(CommandType.TURN_ON, "accendi");
        accendi.setAlias(new String[]{"segnala"});
        getCommands().add(accendi);
        Command insert = new Command(CommandType.INSERT, "inserisci");
        insert.setAlias(new String[]{"inserisci", "metti"});
        getCommands().add(insert);
        Command download = new Command(CommandType.DOWNLOAD, "scarica");
        download.setAlias(new String[]{"scarica", "sposta"});
        getCommands().add(download);
        Command spegni = new Command(CommandType.TURN_OFF, "spegni");
        spegni.setAlias(new String[]{"spegni", "chiudi"});
        getCommands().add(spegni);
        Command ricarica = new Command(CommandType.RELOAD, "ricarica");
        ricarica.setAlias(new String[]{"reload", "ricarica pistola"});
        getCommands().add(ricarica);
        Command accoltellare = new Command(CommandType.STAB, "pugnala");
        accoltellare.setAlias(new String[]{"pugnala zombie", "accoltella"});
        getCommands().add(accoltellare);

        //Protagonista 
        Pg utente = new Pg(20, "protagonista", 100, true);
        setPg(utente);
        if (health != 0) {
            getPg().setHp(health);
        } else {
            getPg().setHp(100);
        }
        //Room
        Room hallway = new Room(1, "Corridoio", 3, true, true);
        hallway.setLook("Il sistema di sicurezza sembra impazzito...ogni volta che uscirai da una stanza la porta verrà chiusa dal sistema di sicurezza del laboratorio ");
        Room room1 = new Room(2, "Stanza numero 1", 4, "La mia vita può durare qualche ora, quello che produco mi divora. Sottile sono veloce, grossa sono lenta e il vento molto mi spaventa. Chi sono?", "candela", true, true);
        room1.setLook("A sud c' è l'uscita che ti riporta al corridoio");
        Room room2 = new Room(3, "Stanza numero 2", 2, true, true);
        room2.setLook("A sud c' è l'uscita che ti riporta al corridoio");
        Room ladder = new Room(4, "scala", 1, false, true);
        ladder.setLook("A sud c' è l'uscita che ti riporta al corridoio ");
        ladder.setIdPorta(1);
        Room strada = new Room(5, "Strada", 5, true, true);
        strada.setLook(" ");
        strada.setIsAperta(true);
        Room casa = new Room(6, "Piano terra", 6, true, false);
        casa.setLook(" ");
        casa.setIsAperta(true);
        Room terrazza = new Room(7, "Terrazza", 7, true, false);
        terrazza.setLook("A sud c' è l'uscita per tornare al piano terra ");
        terrazza.setIsAperta(true);
        Room elicottero = new Room(8, "Elicottero", 8, true, false);
        elicottero.setLook("Un militare ti getta una fune...sali e in fretta ");
        Room hallway2 = new Room(9, "Corridoio piano due", 9, true, false);
        hallway2.setIsAperta(false);
        hallway2.setLook("A sud c' è l'uscita che ti riporta alla scalinata ");
        Room room3 = new Room(10, "Stanza 3", 10, false, false);
        room3.setLook("A sud c' è l'uscita che ti riporta al corridoio ");
        Room room4 = new Room(11, "Stanza 4", 11, "Tutti lo possono aprire, ma nessuno lo sa chiudere. Cosa?", "uovo", true, false);
        room4.setLook("A sud c' è l'uscita che ti riporta al corridoio ");
        room4.setIsAperta(false);
        Room office = new Room(12, "Ufficcio Dottor Smith", 12, true, false);
        office.setIsAperta(false);
        office.setLook("A sud c' è l'uscita che ti riporta al corridoio ");
        Room pcRoom = new Room(13, "pcroom", "Il computer sembra abbastanza recente", 13, true, false, false);
        pcRoom.setLook("Sulla schermata ci sono vari file... chissa' quale sara' quello giusto...riesco a leggere una frase:\n C’è un pullman con 7 ragazze. Ogni ragazza ha 7 zaini. In ogni zaino ci sono 7 gatti grandi. Ogni gatto grande ha 7 gatti piccoli. \nOgni gatto ha 4 gambe. Quante gambe ci sono nel pullman?\n Sicuramente la risposta corretta ti servira'...");
        //add Rooms to RoomList 
        getRooms().add(hallway);
        getRooms().add(room1);
        getRooms().add(room2);
        getRooms().add(ladder);
        getRooms().add(strada);
        getRooms().add(casa);
        getRooms().add(terrazza);
        getRooms().add(elicottero);
        getRooms().add(hallway2);
        getRooms().add(room3);
        getRooms().add(room4);
        getRooms().add(office);
        getRooms().add(pcRoom);

        arrayRoom[0] = hallway;
        arrayRoom[1] = room1;
        arrayRoom[2] = room2;
        arrayRoom[3] = ladder;
        arrayRoom[4] = strada;
        arrayRoom[5] = casa;
        arrayRoom[6] = terrazza;
        arrayRoom[7] = elicottero;
        arrayRoom[8] = hallway2;
        arrayRoom[9] = room3;
        arrayRoom[10] = room4;
        arrayRoom[11] = office;
        arrayRoom[12] = pcRoom;
        //object        
        ObjectGame gun = new ObjectGame(4, "pistola", "è un Revolver", 10, "hallway");
        gun.setAlias(new String[]{"gun", "arma", "pistola"});
        gun.setOpenable(false);
        gun.setPickupable(true);
        gun.setOpen(false);
        hallway.getObjects().add(gun);
        ObjectGame munizioni = new ObjectGame(25, "munizioni", "munizioni per Revolver", 5, "room2");
        munizioni.setAlias(new String[]{"munizioni", "colpi"});
        munizioni.setOpenable(false);
        munizioni.setPickupable(true);
        munizioni.setOpen(false);
        room2.getObjects().add(munizioni);
        ObjectGame coltello = new ObjectGame(26, "coltello da caccia", "e' un coltellaccio sara' sicuramente utile", "casa");
        coltello.setAlias(new String[]{"coltello", "coltello caccia"});
        coltello.setOpenable(false);
        coltello.setPickupable(true);
        coltello.setOpen(false);
        casa.getObjects().add(coltello);
        ObjectGame penDrive = new ObjectGame(15, "pendrive", "e' una sottomarca scadente...600 euro per una torcia e 6 euro per una pendrive...poi sono loro gli scienziati", "room4", 0);
        penDrive.setAlias(new String[]{"pendrive", "pen drive", "chiavetta usb"});
        penDrive.setOpenable(false);
        penDrive.setPickupable(true);
        penDrive.setOpen(false);
        room4.getObjects().add(penDrive);
        ObjectGame syringe = new Siringa(5, "siringa", "è vuota ti consiglio di riempirla con del sangue di zombie", "E' piena di liquido nero...", false, "room1");
        syringe.setAlias(new String[]{"siringa", "ago", "siring", "syringe"});
        syringe.setOpenable(false);
        syringe.setPickupable(true);
        syringe.setOpen(false);
        room1.getObjects().add(syringe);
        ObjectGame goals = new ObjectGame(12, "lista della morte", "E' un pezzo di carta stropicciato,c'e' scritto:\n -Chiuque tu sia raccogli questi oggetti per me:\n Qualcosa per dimenticare quello che e' successo...meglio se forte \n -Un file contenente le mie ricerche che nel tempo ho scordato \n -Un campione di sangue di uno dei miei collaboratori", "room1");
        goals.setAlias(new String[]{"lista", "pezzo di carta","biglietto","bigliettino"});
        goals.setOpenable(false);
        goals.setPickupable(true);
        goals.setOpen(false);
        room1.getObjects().add(goals);
        ObjectGame file1 = new ObjectGame(20, "file1", "10.800", "pcRoom");
        file1.setAlias(new String[]{"file1"});
        file1.setOpenable(false);
        file1.setPickupable(false);
        file1.setOpen(false);
        pcRoom.getObjects().add(file1);
        ObjectGame file2 = new ObjectGame(21, "file2", "10.990", "pcRoom");
        file2.setAlias(new String[]{"file2"});
        file2.setOpenable(false);
        file2.setPickupable(false);
        file2.setOpen(false);
        pcRoom.getObjects().add(file2);
        ObjectGame file3 = new ObjectGame(22, "file3", "9.990", "pcRoom");
        file3.setAlias(new String[]{"file3"});
        file3.setOpenable(false);
        file3.setPickupable(false);
        file3.setOpen(false);
        pcRoom.getObjects().add(file3);
        ObjectGame whiskeyBottle = new BottigliaWhisky(6, "bottiglia di whisky", "è mezza piena o mezza vuota sta a te deciderlo", true, "room2");
        whiskeyBottle.setAlias(new String[]{"bottiglia", "whiskey", "whisky", "alcool"});
        whiskeyBottle.setOpenable(false);
        whiskeyBottle.setPickupable(true);
        whiskeyBottle.setOpen(true);
        room2.getObjects().add(whiskeyBottle);
        ObjectGame letter = new LetteraAddio(7, "lettera", "c'è scritto: Diventerò uno zombie anch'io , questo è tutto opera mia. Ho lavorato a questa mutazione per 10 anni e morirò realizzato", "il migliore in assoluto", "room2");
        letter.setAlias(new String[]{"letter", "lettera", "foglio", "sheet of paper"});
        letter.setOpenable(false);
        letter.setPickupable(true);
        letter.setOpen(true);
        room2.getObjects().add(letter);
        ObjectGame card = new ObjectGame(8, "card", "c'è inciso:'Apre tutte le porte chiuse... ma non tutte tutte'", "tua", "hallway");
        card.setAlias(new String[]{"Card", "card", "Carta", "carta"});
        card.setOpenable(false);
        card.setPickupable(true);
        card.setOpen(false);
        hallway.getObjects().add(card);
        ObjectGame smok = new ObjectGame(9, "fumogeno", "sembra una granata...spero per te non esplodi", "casa");
        smok.setAlias(new String[]{"fumogeno"});
        smok.setOpenable(false);
        smok.setPickupable(true);
        smok.setOpen(false);
        casa.getObjects().add(smok);
        ObjectGame parachute = new ObjectGame(10, "paracadute", "e' molto pesante...stai attento a quando lo apri, avrai un unico tentativo", "elicottero");
        parachute.setAlias(new String[]{"paracadute"});
        parachute.setOpenable(false);
        parachute.setPickupable(true);
        parachute.setOpen(false);
        elicottero.getObjects().add(parachute);
        ObjectGame computer = new ObjectGame(14, "computer", "e' un dispositivo abbastanza recente", "pcRoom");
        computer.setAlias(new String[]{"computer", "pc", "personal computer"});
        computer.setOpenable(false);
        computer.setPickupable(false);
        computer.setOpen(false);
        room4.getObjects().add(computer);
        ObjectGame FlashLight = new FlashLight(13, "torcia", "è una Olight X9R dal costo di 600 euro, se non lo avessi capito è una banale torcia...che spreco di denaro", false, "room2");
        FlashLight.setAlias(new String[]{"torcia", "flashlight", "luce"});
        FlashLight.setOpenable(false);
        FlashLight.setPickupable(true);
        FlashLight.setOpen(false);
        room2.getObjects().add(FlashLight);
        ObjectGame packOfCigarettes = new ObjectGame(11, "pacco sigarette", "non facciamo pubblicità gratuita c'e' la marca, ma se stai per morire usale ti restituiranno un po di energia vitale", "room2", 4);
        packOfCigarettes.setAlias(new String[]{"sigarette", "pacco sigarette", "pacco di sigarette", "pacchetto di sigarette"});
        packOfCigarettes.setOpenable(false);
        packOfCigarettes.setPickupable(true);
        packOfCigarettes.setOpen(true);
        room2.getObjects().add(packOfCigarettes);

        Zombie zombie1 = new Zombie(30, "Max power", "di grosse dimensioni,avrà perso molto tempo in palestra durante la sua vita...", true, 40);
        zombie1.setAlias(new String[]{"non morto", "morto vivente", "vivo non so come", "mollo ma non barcollo"});
        zombie1.setOpenable(false);
        zombie1.setPickupable(false);
        zombie1.setOpen(false);

        LoadInterface Ld = new LoadGame();

        hallway.getZombie().add(Ld.loadZombie(zombie, zombie1));

        Zombie zombie2 = new Zombie(31, "Sten", "i vermi stanno banchettando sol suo corpo", false, 0);
        zombie2.setAlias(new String[]{"non morto", "morto vivente", "vivo non so come", "mollo ma non barcollo"});
        zombie2.setOpenable(false);
        zombie2.setPickupable(false);
        zombie2.setOpen(false);

        room1.getZombie().add(Ld.loadZombie(zombie, zombie2));

        Zombie zombie3 = new Zombie(32, "Michael", "", true, 30);
        zombie3.setAlias(new String[]{"non morto", "morto vivente", "vivo non so come", "mollo ma non barcollo"});
        zombie3.setOpenable(false);
        zombie3.setPickupable(false);
        zombie3.setOpen(false);

        room2.getZombie().add(Ld.loadZombie(zombie, zombie3));


        Zombie zombie5 = new Zombie(34, "Logan", "ti sta guardando con aria affamata", true, 50);
        zombie5.setAlias(new String[]{"non morto", "morto vivente", "vivo non so come", "mollo ma non barcollo"});
        zombie5.setOpenable(false);
        zombie5.setPickupable(false);
        zombie5.setOpen(false);

        hallway2.getZombie().add(Ld.loadZombie(zombie, zombie5));

        Zombie zombie6 = new Zombie(35, "Aaron", "E' ricoperto di sangue... qualcuno lo ha ucciso prima di te", false, 0);
        zombie6.setAlias(new String[]{"non morto", "morto vivente", "vivo non so come", "mollo ma non barcollo"});
        zombie6.setOpenable(false);
        zombie6.setPickupable(false);
        zombie6.setOpen(false);

        room3.getZombie().add(Ld.loadZombie(zombie, zombie6));

        Zombie zombie7 = new Zombie(36, "Brian", "Sembra un neo laureato,che tristezza...morire appena dopo la laurea", true, 40);
        zombie7.setAlias(new String[]{"non morto", "morto vivente", "vivo non so come", "mollo ma non barcollo"});
        zombie7.setOpenable(false);
        zombie7.setPickupable(false);
        zombie7.setOpen(false);

        room4.getZombie().add(Ld.loadZombie(zombie, zombie7));

        Zombie zombie8 = new Zombie(37, "Paul", "Sembra quasi addormentato...", true, 50);
        zombie8.setAlias(new String[]{"non morto", "morto vivente", "vivo non so come", "mollo ma non barcollo"});
        zombie8.setOpenable(false);
        zombie8.setPickupable(false);
        zombie8.setOpen(false);

        office.getZombie().add(Ld.loadZombie(zombie, zombie8));
//le seguenti righe di codice servono in caso di caricamento e sono utili per distinguere gli oggetti posseduti dall'utente daglio oggetti posizionati nelle stanze 
        if (oggetto.isEmpty() == false) {
            List<ObjectGame> objectRoom = new ArrayList<>();
            List<ObjectGame> objectInvenctory = new ArrayList<>();
            for (ObjectGame obgi : oggetto) {
                for (Room k : getRooms()) {
                    for (ObjectGame objs : k.getObjects()) {
                        if (obgi.getId() == objs.getId()) {
                            if (obgi.getCapienza() > 0) {
                                objs.setCapienza(obgi.getCapienza());
                                objectRoom.add(objs);
                                getInventory().add(objs);
                            } else {
                                objectRoom.add(objs);
                                getInventory().add(objs);
                            }

                        }
                    }
                }
            }

            for (ObjectGame fg : objectRoom) {

                if (fg.getPosizione().compareTo("hallway") == 0) {
                    hallway.getObjects().remove(fg);
                } else if (fg.getPosizione().compareTo("room1") == 0) {
                    room1.getObjects().remove(fg);
                } else if (fg.getPosizione().compareTo("room2") == 0) {
                    room2.getObjects().remove(fg);
                } else if (fg.getPosizione().compareTo("ladder") == 0) {
                    ladder.getObjects().remove(fg);
                } else if (fg.getPosizione().compareTo("strada") == 0) {
                    strada.getObjects().remove(fg);
                } else if (fg.getPosizione().compareTo("casa") == 0) {
                    casa.getObjects().remove(fg);
                } else if (fg.getPosizione().compareTo("terrazza") == 0) {
                    terrazza.getObjects().remove(fg);
                } else if (fg.getPosizione().compareTo("elicottero") == 0) {
                    elicottero.getObjects().remove(fg);
                } else if (fg.getPosizione().compareTo("hallway2") == 0) {
                    hallway2.getObjects().remove(fg);
                } else if (fg.getPosizione().compareTo("room3") == 0) {
                    room3.getObjects().remove(fg);
                } else if (fg.getPosizione().compareTo("room4") == 0) {
                    room4.getObjects().remove(fg);
                }

            }
        }

        strada.setNextStep(casa);
        casa.setNorth(terrazza);
        terrazza.setSouth(casa);
        terrazza.setNextStep(elicottero);
        elicottero.setNextStep(hallway);
        hallway.setEast(room1);
        hallway.setWest(room2);
        hallway.setNorth(ladder);
        room1.setSouth(hallway);
        room2.setSouth(hallway);
        ladder.setSouth(hallway);
        ladder.setNorth(hallway2);
        hallway2.setSouth(ladder);
        hallway2.setWest(room3);
        hallway2.setEast(room4);
        hallway2.setNorth(office);
        room3.setSouth(hallway2);
        room4.setSouth(hallway2);
        room4.setNextStep(pcRoom);
        pcRoom.setNextStep(room4);
        office.setSouth(hallway2);

        setCurrentRoom(Ld.loadPosition(position, getRooms()));
    }

    @Override
    public void nextMove(ParserOutput p, PrintStream out) {

        if (p.getCommand() == null) {
            out.println("Non ho capito cosa devo fare! Prova con un altro comando.");
        } else {
            boolean noroom = false;
            boolean move = false;
            if (getPg().getHp() == 0) {
                System.out.println("ULTIMA POSSIBILITA'... BASTA UN PASSO FALSO E SEI K.O.");
            }
            if (getPg().getHp() < 0) {
                System.out.println("hai fallito la missione... è stato un piacere guidarti in questa selva oscura, non essere triste... in pochi riescono a completare la missione");
                System.out.println("RIPROVACI... TI ASPETTO");
                System.exit(0);
            }

            if (p.getCommand().getType() == CommandType.GO) {
                if (getCurrentRoom().getNextStep() != null) {
                    setCurrentRoom(getCurrentRoom().getNextStep());
                    move = true;
                } else {
                    System.out.println("Non puoi entrare qui...");
                }

            } else if (p.getCommand().getType() == CommandType.NORD) {

                if (getCurrentRoom().getVisible() == true) {
                    if (getCurrentRoom().getZombie().isEmpty() == false) {
                        if (getCurrentRoom().getZombie().get(0).checkLife() == false) {
                            System.out.println(getCurrentRoom().getName());

                            if (getCurrentRoom().getIsAperta() == true) {
                                if (getCurrentRoom().getNorth() != null) {
                                    setCurrentRoom(getCurrentRoom().getNorth());
                                    move = true;
                                } else {
                                    noroom = true;
                                }
                            } else {
                                System.out.println("la porta è chiusa,sembra ti occorra una card");
                            }

                        } else {
                            System.out.println("scelta sbagliata gli zombie non sono furbi ma ci vedono");
                            getPg().setHp(getPg().getHp() - getChangeHealth());
                            System.out.println("lo zombi ne approfitta per attaccarti...i tuoi punti vita sono" + getPg().getHp());
                        }
                    } else if (getCurrentRoom().getNorth() != null) {

                        if (getCurrentRoom().getIsAperta() == true) {
                            if (getCurrentRoom().getNorth() != null) {
                                setCurrentRoom(getCurrentRoom().getNorth());
                                move = true;
                            } else {
                                noroom = true;
                            }
                        } else {
                            System.out.println("la porta è chiusa");
                        }

                    } else {
                        System.out.println("Non ti consiglio di muoverti senza vedere chiaramente ciò che ti circonda");
                    }

                } else {
                    System.out.println("non posso muovermi al buio!");
                }

            } else if (p.getCommand().getType() == CommandType.DRAW_BLOOD) {
                boolean checkGotSyringe = false;//Questa variabile booleana serve come flag per verificare la presenza della siringa nell'inventario
                boolean checkSyringe = false;
                if (getCurrentRoom().getZombie().get(0).checkLife() == false) {
                    for (ObjectGame o : getInventory()) {
                        if (o.getName() == "siringa") {
                            checkGotSyringe = true;
                            checkSyringe = true;
                            o.setDescription(o.getCombinedDescription());
                            if (checkGotSyringe == true) {

                                if (o.getFull() == true) {

                                    System.out.println("La siringa è gia piena non c è bisogno che io te lo ricordi vero?");

                                } else {
                                    o.setFull(true);
                                    System.out.println("Bleee....il sangue è nero... ora la siringa è piena,spero per te che non si rompi nei tuoi pantaloni");

                                }
                                checkGotSyringe = false;
                            }

                        }

                    }
                    if (checkSyringe == false) {
                        System.out.println("Non hai la siringa");
                    }

                } else {
                    System.out.println("Mi pare una pessima idea prelevare sangue da uno zombie ancora vivo ");
                }

            } else if (p.getCommand().getType() == CommandType.USE) {
                boolean checkGotObject = false;
                String nameObject = null;

                for (ObjectGame o : getInventory()) {

                    if (o.equals(p.getInvObject())) {
                        checkGotObject = true;
                        nameObject = o.getName();
                    }

                }
                if (checkGotObject == true) {
                    if (nameObject == "card") {
                        if (getCurrentRoom().getLastCommad() == "nord" || getCurrentRoom().getLastCommad() == "ovest") {
                            if (getCurrentRoom().getLastCommad() == "nord") {
                                setCurrentRoom(getCurrentRoom().getNorth());
                                move = true;
                                getCurrentRoom().setIsAperta(true);
                                System.out.println("Hai aperto la porta");
                            } else if (getCurrentRoom().getLastCommad() == "est") {
                                setCurrentRoom(getCurrentRoom().getEast());
                                move = true;
                            } else if (getCurrentRoom().getLastCommad() == "ovest") {
                                setCurrentRoom(getCurrentRoom().getWest());
                                move = true;
                                getCurrentRoom().setIsAperta(true);
                                System.out.println("Hai aperto la porta");
                            } else if (getCurrentRoom().getLastCommad() == "sud") {
                                setCurrentRoom(getCurrentRoom().getSouth());
                                move = true;
                            }
                        } else {
                            System.out.println("La chiave non è wireless avvicinati alla porta");
                        }
                    }
                    if (nameObject == "torcia") {
                        if (getCurrentRoom().getVisible() == false) {
                            getCurrentRoom().setVisible(true);
                            System.out.println("Ora finalmente puoi vedere");
                            if (getCurrentRoom().getZombie().get(0).checkLife() == true) {
                                System.out.println("Stai attento ora riesco a vedere uno zombie e credo che anche lui ti abbia visto");
                            }
                        } else {
                            System.out.println("La stanza è già illuminata");
                        }
                    }
                    if (nameObject == "coltello da caccia") {
                        for (ObjectGame o : getInventory()) {
                            if (o.getName().compareTo("coltello da caccia") == 0) {
                                if (getCurrentRoom().getZombie().get(0).getHpZombie() >= 1) {
                                    getCurrentRoom().getZombie().get(0).setHp(getCurrentRoom().getZombie().get(0).getHp()-getChangeHealth());
                                    System.out.println("la vita dello zombie è " + getCurrentRoom().getZombie().get(0).getHpZombie());
                                    getPg().setHp(getPg().getHp() - getChangeHealth());
                                    System.out.println("lo zombie contrattacca");
                                    System.out.println("i tuoi punti vita sono" + getPg().getHp());
                                    getPg().setHp(getPg().getHp() - getChangeHealth());
                                    System.out.println("lo zombie contrattacca");
                                    System.out.println("i tuoi punti vita sono" + getPg().getHp());
                                } else {
                                    System.out.println("lo zombie è morto");
                                    getCurrentRoom().getZombie().get(0).isDead();
                                }

                            }
                        }
                    }
                    if (nameObject == "fumogeno") {
                        if (p.getInvObject().getName().compareTo("fumogeno") == 0) {
                            if (getCurrentRoom().getName().compareTo("Terrazza") == 0) {
                                System.out.println("L'elicottero ha notato il tuo fumogeno e si sta avvicinando...");
                                System.out.println("Un militare ti getta una fune...sali e in fretta");
                            } else {
                                System.out.println("Non ti consiglio di usarlo qui a meno che tu non voglia peggiorare la situazione");
                            }
                        }

                    }
                } else {
                    System.out.println("Non hai questo oggetto");
                }

            } else if (p.getCommand().getType() == CommandType.SHOOT) {  //COMBATTIMENTO ZOMBIE VS UTENTE
                boolean ceckGotGun = false;
                boolean checkGotMunition = false;                
                    for (ObjectGame o : getInventory()) {//proiettili
                        if (o.getName() == "pistola") {
                            ceckGotGun = true;
                            o.setCapienza(o.getCapienza() - 1);

                            if (o.getCapienza() > 0) {
                                checkGotMunition = true;
                            }
                        }
                    }
                    if (checkGotMunition == true) {
                        if (getCurrentRoom().getZombie().get(0).checkLife() == true) {
                            if (ceckGotGun == true) {
                                getCurrentRoom().getZombie().get(0).setDannoZombieHp();
                                if (getCurrentRoom().getZombie().get(0).getHpZombie() > 1) {
                                    System.out.println("lo hai preso alla gamba");
                                    System.out.println("la vita dello zombie è" + getCurrentRoom().getZombie().get(0).getHpZombie());
                                    getPg().setHp(getPg().getHp() - getChangeHealth());
                                    System.out.println("lo zombie contrattacca");
                                    System.out.println("i tuoi punti vita sono" + getPg().getHp());
                                } else {
                                    System.out.println("lo zombie è morto");
                                    getCurrentRoom().getZombie().get(0).isDead();
                                }
                            } else {
                                getPg().setHp(getPg().getHp() - getChangeHealth());
                                System.out.println("non possiedi una pistola");
                                System.out.println("lo zombi ne approfitta per attaccarti...i tuoi punti vita sono" + getPg().getHp());
                            }
                        } else {
                           System.out.println("Lo zombie è già morto");
                        }
                    } else {
                        if (getCurrentRoom().getZombie().get(0).getHpZombie() >= 1) {
                            System.out.println("Ops hai esaurito i colpi...");

                            getPg().setHp(getPg().getHp() - getChangeHealth());
                            System.out.println("lo zombie ne approfitta");
                            System.out.println("i tuoi punti vita sono" + getPg().getHp());
                        } else {
                            System.out.println("lo zombie è morto");
                            getCurrentRoom().getZombie().get(0).isDead();

                        }
                    }
                
            } else if (p.getCommand().getType() == CommandType.STAB) {
                for (ObjectGame o : getInventory()) {
                    if (o.getName().compareTo("coltello da caccia") == 0) {
                        if (getCurrentRoom().getZombie().get(0).getHpZombie() >0) {
                            getCurrentRoom().getZombie().get(0).setHp(getCurrentRoom().getZombie().get(0).getHp()-getChangeHealth());
                            System.out.println("la vita dello zombie è " + getCurrentRoom().getZombie().get(0).getHpZombie());
                            getPg().setHp(getPg().getHp() - getChangeHealth());
                            System.out.println("lo zombie contrattacca");
                            System.out.println("i tuoi punti vita sono" + getPg().getHp());
                        } else {
                            System.out.println("lo zombie è morto");
                            getCurrentRoom().getZombie().get(0).isDead();
                        }

                    }
                }
            } else if (p.getCommand().getType() == CommandType.SOUTH) {
                 if (getCurrentRoom().getZombie().isEmpty() == false) {
                    if (getCurrentRoom().getZombie().get(0).checkLife() == false) {
                        if (getCurrentRoom().getSouth() != null) {
                            setCurrentRoom(getCurrentRoom().getSouth());
                            move = true;
                        } else {
                            noroom = true;
                        }
                    } else {
                        System.out.println("scelta sbagliata gli zombie non sono furbi ma ci vedono");
                        getPg().setHp(getPg().getHp() - getChangeHealth());
                        System.out.println("lo zombi ne approfitta per attaccarti...i tuoi punti vita sono" + getPg().getHp());
                    }
                } else {
                    if (getCurrentRoom().getSouth() != null) {
                        setCurrentRoom(getCurrentRoom().getSouth());
                        move = true;
                    } else {
                        noroom = true;
                    }

                }
            } else if (p.getCommand().getType() == CommandType.EAST) {

                if (getCurrentRoom().getZombie().isEmpty() == false) {
                    if (getCurrentRoom().getZombie().get(0).checkLife() == false) {

                        if (getCurrentRoom().getIsAperta() == true) {
                            if (getCurrentRoom().getEast() != null) {
                                setCurrentRoom(getCurrentRoom().getEast());
                                move = true;
                            } else {
                                noroom = true;
                            }
                        } else {
                            System.out.println("La porta è chiusa,c'è un indovinello");

                        }
                    } else {
                        System.out.println("scelta sbagliata gli zombie non sono furbi ma ci vedono");
                        getPg().setHp(getPg().getHp() - getChangeHealth());
                        System.out.println("lo zombi ne approfitta per attaccarti...i tuoi punti vita sono" + getPg().getHp());
                    }
                } else if (getCurrentRoom().getEast() != null) {
                    if (getCurrentRoom().getIsAperta() == true) {
                        if (getCurrentRoom().getEast() != null) {
                            setCurrentRoom(getCurrentRoom().getEast());
                            move = true;
                        } else {
                            noroom = true;
                        }
                    } else {
                        System.out.println("La porta è chiusa,c'è un indovinello");

                    }
                } else {
                    noroom = true;
                }
            } else if (p.getCommand().getType() == CommandType.WEST) {
                if (getCurrentRoom().getZombie().isEmpty() == false) {
                    if (getCurrentRoom().getZombie().get(0).checkLife() == false) {
                        if (getCurrentRoom().getIsAperta() == true) {
                            if (getCurrentRoom().getWest() != null) {
                                setCurrentRoom(getCurrentRoom().getWest());
                                move = true;
                            } else {
                                noroom = true;
                            }
                        } else {
                            System.out.println("la porta è chiusa,sembra ti occorra una card");
                        }
                    } else {
                        System.out.println("scelta sbagliata gli zombie non sono furbi ma ci vedono");
                        getPg().setHp(getPg().getHp() - getChangeHealth());
                        System.out.println("lo zombi ne approfitta per attaccarti...i tuoi punti vita sono" + getPg().getHp());
                    }
                } else if (getCurrentRoom().getWest() != null) {

                    if (getCurrentRoom().getIsAperta() == true) {
                        if (getCurrentRoom().getWest() != null) {
                            setCurrentRoom(getCurrentRoom().getWest());
                            move = true;
                        } else {
                            noroom = true;
                        }
                    } else {
                        System.out.println("la porta è chiusa,sembra occorra una card");
                    }

                }
            } else if (p.getCommand().getType() == CommandType.INVENTORY) {
                if (getInventory().isEmpty() == true) {
                    System.out.println(" non hai oggetti nel tuo inventario!");
                } else {
                    out.println("Nel tuo inventario ci sono:");
                    for (ObjectGame o : getInventory()) {
                        if (o.getName().compareTo("pistola") == 0) {
                            out.println(o.getName() + " : nel caricatore ci sono " + o.getCapienza() + " colpi");
                        } else {
                            out.println(o.getName() + ": " + o.getDescription());
                        }

                    }
                }
            } else if (p.getCommand().getType() == CommandType.RELOAD) {
                boolean checkPistola = false;
                boolean checkMunizioni = false;
                int colpi = 0;
                for (ObjectGame o : getInventory()) {
                    if (o.getName().compareTo("pistola") == 0) {
                        checkPistola = true;
                    }
                    if (o.getName().compareTo("munizioni") == 0) {
                        checkMunizioni = true;
                        colpi = o.getCapienza();
                    }
                }
                if ((checkMunizioni = true) && (checkPistola = true)) {
                    int ceckSuccesReload = 0;
                    int cartucce = 0;
                    int index = 0;//Variabile che memorizza l'indice della posizione di un determinato oggetto
                    int i = 0;
                    for (ObjectGame o : getInventory()) {

                        if (o.getName().compareTo("pistola") == 0) {
                            o.setCapienza(o.getCapienza() + colpi);
                            cartucce = o.getCapienza();
                        }
                        if (o.getName().compareTo("munizioni") == 0 && o.getCapienza() > 0) {
                            getCurrentRoom().getObjects().add(o);
                            ceckSuccesReload = 1;
                            o.setCapienza(0);
                            index = i;
                        }
                        i++;
                    }
                    if (ceckSuccesReload == 1) {
                        System.out.println("Ricarica effettuata");
                        System.out.println("Hai a disposizione " + cartucce + " colpi per la pistola");
                        System.out.println("la cartuccia vuota è stata rimossa dal tuo inventario");
                        getInventory().remove(index);
                    } else {
                        System.out.println("non hai delle munizioni!");
                    }

                } else {
                    System.out.println("impossibile ricaricare l'arma");
                }
            } else if (p.getCommand().getType() == CommandType.TURN_OFF) {
                if (getCurrentRoom().getName().compareTo("pcroom") == 0) {
                    System.out.println("Che bravo ragazzo si preoccupa del mondo... hai spento il pc ");
                    setCurrentRoom(getCurrentRoom().getNextStep());
                    move = true;
                }

            } else if (p.getCommand().getType() == CommandType.DOWNLOAD) {
                if (getCurrentRoom().getUsbPort() == true) {

                    if (p.getObject().getName().compareTo("file2") == 0) {

                        for (ObjectGame o : getInventory()) {
                            if (o.getName().compareTo("pendrive") == 0) {
                                o.setCapienza(1);
                                System.out.println("Hai scaricato una copia del file e la pen drive e' stata scollegata");
                                getCurrentRoom().setUsbPort(false);
                            }
                        }
                    } else {
                        System.out.println("Hai scaricato una copia del file e la pen drive e' stata scollegata");
                        getCurrentRoom().setUsbPort(false);
                    }
                } else {
                    System.out.println("Non hai inserito la pendrive nel pc");
                }
            } else if (p.getCommand().getType() == CommandType.INSERT) {
                boolean checkGotPenDrive = false;
                for (ObjectGame o : getInventory()) {
                    if (o.getName().compareTo("pendrive") == 0) {
                        checkGotPenDrive = true;

                    }
                }
                if (checkGotPenDrive == true) {
                    getCurrentRoom().setUsbPort(true);
                    System.out.println("Dopo vari tentativi girando e rigirando il verso della chiavetta riesci a centrare il buco...");
                } else {
                    System.out.println("Non hai la pendrive nel tuo inventario");
                }
            } else if (p.getCommand().getType() == CommandType.GO_UP) {
                if (getCurrentRoom().getName().compareTo("Terrazza") == 0 && getCurrentRoom().getNextStep() != null) {//controllo del comando sali.il personaggio deve lanciarsi effettivamente solo se si trova in terrazza  

                    System.out.println("Goffamente riesci a salirci ahahahah... e pensare che il mondo sta scommettendo su di te");
                    setCurrentRoom(getCurrentRoom().getNextStep());
                    move = true;
                } else {
                    System.out.println("non ha molto senso quello che stai dicendo");//personaggio sale ma non si trova in terrazza
                }

            } else if (p.getCommand().getType() == CommandType.LAUNCH) {

                boolean checkGotParachute = false;
                if (getCurrentRoom().getName().compareTo("Elicottero") == 0) {//controllo del comando lancia.il personaggio deve lanciarsi effettivamente solo se si trova sull'elicottero
                    for (ObjectGame o : getInventory()) {
                        if (o.getName().compareTo("paracadute") == 0) {
                            checkGotParachute = true;
                        }
                    }
                    if (checkGotParachute == true) {
                        System.out.println("Il complesso dall' alto sembra immenso... atterri nel giardino davanti l' ingresso.\n La porta dell' ingresso e' aperta... ti fai coraggio ed entri");
                        setCurrentRoom(getCurrentRoom().getNextStep());
                        move = true;
                    } else {
                        System.out.println("E' scientificamente provato che i giocatori che si buttano da altezze considerevoli senza paracadute muoiono...per saperne di piu' visita il sito millemodipermorire.com");
                    }
                } else {
                    System.out.println("finisci a terra! ottima scelta.... grasse risate....  che figura di merda :(");//personaggio si lancia ma non si trova sull'elicottero
                }

            } else if (p.getCommand().getType() == CommandType.TURN_ON) {
                boolean checkPcRoom = false;
                boolean checkGot = false;
                for (ObjectGame o : getCurrentRoom().getObjects()) {
                    if (o.getId() == 14) {
                        checkPcRoom = true;

                    }
                }
                if (checkPcRoom == true) {

                    //if(p.getInvObject().getName().compareTo("computer")==0 && getCurrentRoom().getName().compareTo("room4")==0){
                    System.out.println("Hai acceso il pc...fa un casino incredibile");
                    System.out.println("Da questo momento in poi per distogliere il mio sguardo dallo schermo dovrai chiedermi di spegnere il computer");
                    setCurrentRoom(getCurrentRoom().getNextStep());
                    move = true;
                //}
                }else{
                for (ObjectGame o : getInventory()) {
                    if ((o.getName().compareTo("torcia") == 0) || (o.getName().compareTo("fumogeno") == 0)) {
                        checkGot = true;
                    }
                }
                if (checkGot == true) {
                    if (p.getInvObject().getName().compareTo("fumogeno") == 0) {
                        if (getCurrentRoom().getName().compareTo("Terrazza") == 0) {
                            System.out.println("L'elicottero ha notato il tuo fumogeno e si sta avvicinando...");
                            System.out.println("Un militare ti getta una fune...sali e in fretta");
                        } else {
                            System.out.println("Non ti consiglio di usarlo qui a meno che tu non voglia peggiorare la situazione");
                        }
                    } else if (p.getInvObject().getName().compareTo("torcia") == 0) {
                        if (getCurrentRoom().getVisible() == false) {
                            getCurrentRoom().setVisible(true);
                            System.out.println("Ora finalmente puoi vedere");
                            if (getCurrentRoom().getZombie().get(0).checkLife() == true) {
                                System.out.println("Stai attento ora riesco a vedere un putrido e credo che anche lui ti abbia visto");
                            }
                        } else {
                            System.out.println("La stanza è già illuminata");
                        }
                    }

                } else {
                    System.out.println("non possiedi nulla da accendere");
                }
                }
            } else if (p.getCommand().getType() == CommandType.SMOKE) {
                if (getCurrentRoom().getName().compareTo("pcRoom") == 0) {
                    System.out.println("non puoi fumare mentre sei al pc");
                } else {
                    boolean checkGotSigarette = false;//variabile che controlla se ci sono sigarette nell'inventario
                    boolean got = false;//variabile che memorizza la presenza delle sigarette nell'inventario
                    for (ObjectGame o : getInventory()) {
                        if (o.getName().compareTo("pacco sigarette") == 0) {
                            checkGotSigarette = true;
                        } else {
                            checkGotSigarette = false;
                        }

                        if (checkGotSigarette == true) {
                            got = true;
                            if (getPg().getHp() < 100) {//controllo che verifica se i punti vita del personaggio siano gia al massimo
                                if (o.getCapienza() > 0) {// controllo sul numero delle sigarette presenti nel pacchetto

                                    getPg().setHp(getPg().getHp() + getChangeHealth());//incremento della salute del personaggio
                                    o.setCapienza(o.getCapienza() - 1);
                                    if (getCurrentRoom().getZombie().get(0).checkLife() == false) {
                                        System.out.println("Fiuuu....non è molto salutare ma ti tira su....ora hai " + getPg().getHp() + " punti ferita");
                                    } else {
                                        System.out.println("Che sfrontato ti accendi una sigaretta mentre uno zombie vuole mangiarti...");
                                        System.out.println("Fiuuu....non è molto salutare ma ti tira su....ora hai " + getPg().getHp() + " punti ferita");
                                    }

                                    System.out.println("Ti sono rimaste " + o.getCapienza() + "sigarette");

                                } else {
                                    System.out.println("hai terminato le tutte le sigarette");
                                }

                            } else {
                                System.out.println("i tuoi punti vita sono al Top , conserva le sigarette per un altro momento");
                            }
                        }
                        checkGotSigarette = false;

                    }
                    if (got == false) {
                        System.out.println("Non hai le sigarette nel tuo inventario");
                    }
                }
            } else if (p.getCommand().getType() == CommandType.LEAVE) {
                boolean checkGot = false;//Variabile utilizzata nella verifica della presenza dell'oggetto da lasciare nell'inventario
                for (ObjectGame o : getInventory()) {

                    if (o.equals(p.getInvObject())) {

                        checkGot = true;
                        out.println("Hai lasciato: " + p.getInvObject().getName());

                    }

                }
                if (checkGot == true) {
                    getInventory().remove(p.getInvObject());
                    getCurrentRoom().getObjects().add(p.getInvObject());

                }
                if (checkGot == false) {
                    System.out.println("non possiedi quest'oggetto.");
                }

                if (getCurrentRoom().getName() == "Ufficcio Dottor Smith") {
                    boolean checksiringa = false;
                    boolean checkbottiglia = false;
                    boolean checkpendrive = false;
                    for (ObjectGame y : getCurrentRoom().getObjects()) {
                        if (y.getName() == "siringa") {//Queste righe di codice verificano che l'utente lasci nella stanza del professore gli oggetti necessari
                            if (y.getFull() == true) {
                                checksiringa = true;
                            }
                        }
                        if (y.getName() == "bottiglia di whisky") {
                            checkbottiglia = true;
                        }
                        if (y.getName() == "pendrive") {
                            if (y.getCapienza() == 1) {
                                checkpendrive = true;
                            }
                        }
                    }
                    if (checksiringa == true && checkbottiglia == true && checkpendrive == true) {
                        System.out.println("Il professore sta uscendo dal suo nascondiglio.....");
                        System.out.println("Eccolo finalmente!!!");
                        System.out.println("Sembra abbastanza soddisfatto degli oggetti che hai lasciato nel suo ufficio, soprattutto della bottiglia di whisky"); 
                        System.out.println("E ti dice<<Grazie per la missione che hai portato a termine, hai salvato il mondo>>");
                        System.out.println("ora è la tua guida che parla... non avrei scommesso un solo euro su di te ma hai smentito tutte le mie aspettative");
                        System.out.println("COMPLIMENTI");
                        System.exit(0);
                    }

                }

            } else if (p.getCommand().getType() == CommandType.LOOK_AT) {
                if (getCurrentRoom().getVisible() == true) {
                    if (getCurrentRoom().getObjects().isEmpty() == false) {
                        out.println(getCurrentRoom().getLook());
                        out.println("Riesci a vedere:");
                        for (ObjectGame o : getCurrentRoom().getObjects()) {

                            System.out.println(o.getName() + ":" + o.getDescription());

                        }

                    } else {
                        System.out.println("non c'e' nulla che ti possa servire");
                    }
                } else {
                    System.out.println("Non riesco a vedere nulla ti ho già detto che non vedo un cazzo");
                }
            } else if (p.getCommand().getType() == CommandType.OPEN) {
                System.out.println("Non puoi aprire o sfondare le porte...ogni porta ha un sistema di sicurezza particolare");
            }else if (p.getCommand().getType() == CommandType.ESCAPE) {
                System.out.println("Ti sembra il caso di scappare...il mondo conta su di te");
            } else if (p.getCommand().getType() == CommandType.READ) {
                if ((p.getInvObject() == null) || (p.getInvObject().getName().compareTo("indovinello") == 0)) {
                    if (getCurrentRoom().getLastCommad() == "nord" || getCurrentRoom().getLastCommad() == "sud" || getCurrentRoom().getLastCommad() == "est" || getCurrentRoom().getLastCommad() == "ovest") {

                        if (getCurrentRoom().getLastCommad() == "nord") {
                            setCurrentRoom(getCurrentRoom().getNorth());
                            move = true;
                        } else if (getCurrentRoom().getLastCommad() == "est") {

                            String scelta;//Stringa che memorizza la risposta dell'utente
                            boolean fineciclo = false;//Variabile booleana che termina il ciclo una volta aperta la porta
                            String answer;//Stringa che memorizza la risposta all'indovinello dell'utente
                            int checkAnswer = 0;//Verifica la correttezza della risposta inserita

                            setCurrentRoom(getCurrentRoom().getEast());
                            move = true;
                            System.out.println(getCurrentRoom().getIndovinello());
                            do {
                                System.out.println("Qual è la tua risposta: ");
                                Scanner scanner = new Scanner(System.in);
                                answer = scanner.nextLine();

                                if (getCurrentRoom().checkIndovinello(answer) == 0) {
                                    getCurrentRoom().setIsAperta(true);
                                    System.out.println("Hai aperto la porta ");
                                    fineciclo = false;
                                } else {

                                    System.out.println("La risposta è errata babbo di minchia, vuoi riprovare?:");
                                    scelta = scanner.nextLine();
                                    do {
                                        if (scelta.compareTo("no") == 0) {
                                            checkAnswer = 1;
                                        } else if (scelta.compareTo("si") == 0) {
                                            checkAnswer = 1;

                                        }

                                    } while (checkAnswer == 0);

                                    if (scelta.compareTo("no") == 0) {
                                        fineciclo = false;
                                        setCurrentRoom(getCurrentRoom().getSouth());
                                        move = true;

                                    } else {
                                        fineciclo = true;

                                    }
                                }
                            } while (fineciclo == true);

                        } else if (getCurrentRoom().getLastCommad() == "ovest") {
                            setCurrentRoom(getCurrentRoom().getWest());
                            move = true;
                        } else if (getCurrentRoom().getLastCommad() == "sud") {
                            setCurrentRoom(getCurrentRoom().getSouth());
                            move = true;
                        }

                    } else {
                        System.out.println("Avvicinati imbecille");
                    }
                } else {
                    if (p.getInvObject().getName().compareTo("lista della morte") == 0) {
                        boolean checkGotList = false;
                        for (ObjectGame o : getInventory()) {
                            if (o.getName().compareTo("lista della morte") == 0) {
                                System.out.println(o.getDescription());
                                checkGotList = true;
                            }
                        }
                        if (checkGotList == false) {
                            System.out.println("Non hai la lista nell' inventario ");
                        }
                    }
                }
            } else if (p.getCommand().getType() == CommandType.PICK_UP) {
                if (getCurrentRoom().getVisible() == true) {

                    if (p.getObject() != null) {
                        if (p.getObject().isPickupable()) {
                            if (getInventory().size() <= 6) {
                                getInventory().add(p.getObject());
                                getCurrentRoom().getObjects().remove(p.getObject());
                                out.println("Hai raccolto: " + p.getObject().getName() + "->" + p.getObject().getDescription());
                            } else {
                                out.println("Il tuo inventario è pieno... meglio non appesantirti");
                            }
                        } else {
                            out.println("Non puoi raccogliere questo oggetto.");
                        }
                    } else {

                        System.out.println("non vedo questo oggetto in questa stanza.... hai le allucinazioni?");

                    }
                } else {
                    System.out.println("Non riesco a vedere nulla ti ho già detto che non vedo un cazzo");
                }
            }

            if (noroom) {

                if (getCurrentRoom().getVisible() == true) {
                    out.println(" Da quella parte non si puo' andare c'e' un muro! ");
                } else {
                    out.println("Vai a sbattere contro il muro e ti fai parecchio male...");
                    getPg().setHp(getPg().getHp() - getChangeHealth());
                    out.println("Adesso possiedi:" + getPg().getHp() + "punti ferita");
                }

                if (getCurrentRoom().getName().compareTo("pcroom") != 0) {
                } else {
                    System.out.println("Dove vai maleducato senza spegnere il computer, nessuno ti ha insegnato il risparmio energetico");
                }
            } else if (move) {
                out.println(getCurrentRoom().getName());
                out.println("================================================");
                AssignDescription o = new AssignDescription();
                try {
                    
                    if (getCurrentRoom().getName().compareTo("pcroom") != 0) {
                        System.out.println(o.managerDescription(getCurrentRoom().getId()));
                        System.out.println("Cosa devo fare? ");
                        if (getCurrentRoom().getVisible() == true) {
                            if (getCurrentRoom().getZombie().isEmpty() == false) {
                                if (getCurrentRoom().getZombie().get(0).checkLife() == false) {
                                    System.out.println("Rilassati non riesco a vedere zombie vivi");
                                } else {
                                    System.out.println("Stai attento riesco a vedere uno zombie che si sta muovendo");
                                    System.out.println("è uno zombie " + getCurrentRoom().getZombie().get(0).getName() + "sulla targhetta riesci a leggere " + getCurrentRoom().getZombie().get(0).getDescription());
                                }
                            }
                        } else {
                            System.out.println("Non riesco a vedere nulla,credo anche tu... e' troppo buio, ti servirebbe qualcosa per fare luce");
                        }
                    } else {
                        System.out.println(getCurrentRoom().getDescription());
                    }

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(KillZombie.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            getCurrentRoom().setLastCommand(p.getCommand().getName());
        }

    }

}
