package Wade.botti;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.util.Snowflake;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

public class Botti {

    private final DiscordClient client = new DiscordClientBuilder("Removed").build();
    private ArrayList<User> users = new ArrayList<>();

    public Botti() {
        LoadDatabase();

        client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(new Consumer<ReadyEvent>() {
                               public void accept(ReadyEvent ready) {
                                   System.out.println("Logged in as " + ready.getSelf().getUsername());
                               }
                           }
                );

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                            if (UserIsCurrentBot(event.getMessage().getAuthor())) {
                                return;
                            }

                            Message message = event.getMessage();
                            User kayttaja = HaeTaiLuoKayttaja(message.getAuthor());
                            kayttaja.messagePoints++;
                            SaveDatabase();

                            String Komento = message.getContent().get();

                            if (Komento.equalsIgnoreCase("<Help")) {
                                message.getChannel().block().createMessage("<Big Baller: Tells Who is Balling the hardest atm!").block();
                                message.getChannel().block().createMessage("<Reminder: Reminds you about the gamer-frendly race to the Biggest Baller of the month title. ").block();
                                message.getChannel().block().createMessage("<ping: Will hail to the king ").block();
                                message.getChannel().block().createMessage("<points: Tells you your personal points").block();
                                message.getChannel().block().createMessage("<desrois: Compliments The Greates Person alive #8").block();
                                message.getChannel().block().createMessage("<jtde: Will blow your mind").block();
                                message.getChannel().block().createMessage("<bread: will give u sum bred").block();
                                message.getChannel().block().createMessage("<u_u: Kallu").block();
                                message.getChannel().block().createMessage("<owo: Happy Surprise").block();
                                message.getChannel().block().createMessage("<game").block();
                            }

                            if (Komento.equalsIgnoreCase("<game")) {
                                message.getChannel().block().createMessage("@Funnel the Kalle").block();
                            }

                            if (Komento.equalsIgnoreCase("owo")) {
                                message.getChannel().block().createMessage("You are banned from the server").block();
                            }

                            if (Komento.equalsIgnoreCase("<u_u")) {
                                message.getChannel().block().createMessage("Kalle havumäki ei ole vammainen hän on vain lievästi kehitysvammainen..").block();
                            }

                            if (Komento.equalsIgnoreCase("<bread")) {
                                message.getChannel().block().createMessage("Nyt täytyy kyl laittaa musat tulille, ei helefetti, , , VILJAMIII!!!!").block();
                            }

                            if (Komento.equalsIgnoreCase("<jtde")) {
                                message.getChannel().block().createMessage("Joel starts opel...  vRGGNN vRGGNN ... hes gone...").block();
                            }

                            if (Komento.equalsIgnoreCase("<desrois")) {
                                message.getChannel().block().createMessage("He is just awesome bosser!").block();
                            }

                            if (Komento.equalsIgnoreCase("<Reminder")) {
                                message.getChannel().block().createMessage(" The Biggest Baller of the Month Will Be Awarded with a surprise price!!!").block();
                            }

                            if (Komento.equalsIgnoreCase("<Big baller")) {
                                int Suurin = enitenPisteitä();
                                long Nimi = enitenPisteitäNimi();

                                message.getChannel().block().createMessage(client.getUserById(Snowflake.of(Nimi)).block().getUsername() + " is the BIGGEST BALLER WITH: " + Suurin + " Points!").block();
                            }

                            if (Komento.equalsIgnoreCase("<points")) {
                                message.getChannel().block().createMessage(message.getAuthor().get().getUsername() + " Has: " + kayttaja.messagePoints + " points!").block();
                            }

                            if (Komento.equalsIgnoreCase("<ping")) {
                                message.getChannel().block().createMessage("Joppeee!").block();
                            }
                        }
                );

        client.login().block();
    }

    public void SaveDatabase() {
        try {
            FileOutputStream fos = new FileOutputStream("userdata");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LoadDatabase() {
        try {
            FileInputStream fis = new FileInputStream("userdata");
            ObjectInputStream ois = new ObjectInputStream(fis);

            users = (ArrayList) ois.readObject();

            ois.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            System.out.println("Class not found");
            e1.printStackTrace();
        }
    }

    public boolean UserIsCurrentBot(Optional<discord4j.core.object.entity.User> user) {
        return user.get().getId().asLong() == client.getSelfId().get().asLong();
    }

    public User HaeTaiLuoKayttaja(Optional<discord4j.core.object.entity.User> user) {
        return HaeTaiLuoKayttaja(user.get().getId().asLong());
    }

    public User HaeTaiLuoKayttaja(long userId) {
        for (User user : users) {
            if (userId == user.userId) {
                return user;
            }
        }

        if (users.add(new User(userId, 0))) {
            return HaeTaiLuoKayttaja(userId);
        }

        return null;
    }

    public int enitenPisteitä() {
        int points = 0;

        for (User user : users) {
            if (points < user.messagePoints) {
                points = user.messagePoints;
            }
        }

        return points;
    }

    public Long enitenPisteitäNimi() {
        long Suurin = 0;
        int points = 0;

        for (User user : users) {
            if (points < user.messagePoints) {
                points = user.messagePoints;
                Suurin = user.userId;
            }
        }

        return Suurin;
    }
}