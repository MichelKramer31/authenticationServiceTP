package authenticationService;

import client.ClientTCP;
import jdk.nashorn.internal.AssertsEnabled;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;

class AuthenticationServiceAppTest {

    private void simulateWrite(@NotNull String toWrite){
        System.setIn(new ByteArrayInputStream(toWrite.getBytes()));
    }

    @BeforeAll
    public static void lauchAuthenticationServiceApp() throws Exception{
        Thread applicationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                AuthenticationServiceApp.main(new String[]{});
            }
        });
        applicationThread.start();
    }

    @BeforeEach
    public void lauchTCPClient() throws Exception{
        ClientTCP.main(new String[]{});
        //simulateWrite("28415");
        System.setIn(new ByteArrayInputStream("28415".getBytes()));

    }

    @Test
    public void testChecker(){

    }


    @AfterEach
    void tearDown() {
    }
}