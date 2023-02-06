package com.dh.userprofile.service.impl;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.dh.userprofile.model.User;
import com.dh.userprofile.model.UserLoginDTO;
import com.dh.userprofile.model.UserVerifyDTO;
import com.dh.userprofile.repository.UserRepository;
import com.dh.userprofile.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

import com.google.common.collect.Maps;
import org.springframework.util.ReflectionUtils;

@Service
public class UserServiceImpl implements IUserService {

//    @Value("${aliaspath}")
//    public String path;

    List<String> alias;
    Random random = new Random();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AWSCognitoIdentityProvider cognitoClient;

    public UserServiceImpl(AWSCognitoIdentityProvider cognitoClient, @Value("aws.cognito.clientId") String clientId ){
        this.cognitoClient = cognitoClient;
        this.clientId = clientId;
    }

    private String clientId;

    @Override
    public User save(User user) {


       String userSub = this.SignUp(user);
       user.setUserSub(userSub);

        user.setCvu(getRandomNumberString());
        initAlias();

        user.setAlias(nextAlias());
        user.setPassword("0");
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getByID(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getByIdSub(String idSub) {
        return userRepository.findByuserSub(idSub);
    }

    public String getRandomNumberString() {

        String base = "00000302";
        String numericString = "0123456789";

        StringBuilder sb = new StringBuilder(22);

        sb.append(base);

        while (sb.length() < 22){
            int index = (int) (numericString.length() * Math.random());

            sb.append(numericString.charAt(index));
        }

        return sb.toString();
    }


   /* public RandomAliasGenerator(Random random){
        initAlias();
        initRandom(random);
    }*/

    private void initAlias (){
        String path = new File("user-profile/src/main/resources/static/aliasnames.txt").getAbsolutePath();
        //File nameFile = new File("aliasnames.txt");
        File nameFile = new File("path");
       alias = fileToList(nameFile);
    }


    private List<String> fileToList (File file) {
        List<String> list = new ArrayList<String>();

        FileReader fileReader = null;
        BufferedReader in = null;
        try {
            fileReader = new FileReader(file);
            in = new BufferedReader(fileReader);

            String line;
            while ((line = in.readLine()) != null)
                list.add(line);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ioe) {

                }
            }
        }
        return list;
    }

    public String nextAlias() {
        int a = random.nextInt(alias.size());
        int b = random.nextInt(alias.size());
        int c = random.nextInt(alias.size());


        return alias.get(a) + "." + alias.get(b) + "." + alias.get(c);
    }


    private String SignUp(User user){

        SignUpResult signUpResult = cognitoClient.signUp(
                new SignUpRequest()
                        .withClientId("klua53s184h8i2ceu415vqemi") //Identificador unico de nuestro UserPool en cognito
                        .withUsername(user.getName())
                        .withPassword(user.getPassword())
                        .withUserAttributes(new AttributeType()
                                .withName("email").withValue(user.getEmail())));


        return signUpResult.getUserSub();

    }


    public Boolean verifyUser(UserVerifyDTO userVerifyDTO){

        try{
            cognitoClient.confirmSignUp(new ConfirmSignUpRequest().withClientId("klua53s184h8i2ceu415vqemi")
                    .withUsername(userVerifyDTO.getUserName())
                    .withConfirmationCode(userVerifyDTO.getUserCode()));
        }
        catch (Exception e){
            return false;
        }
        return true;
    }


    @Override
    public AuthenticationResultType userLogin(UserLoginDTO userLoginDTO){


            final Map<String, String> authParams = Maps.newHashMap();
            authParams.put("USERNAME", userLoginDTO.getUserName());
            authParams.put("PASSWORD", userLoginDTO.getPassword());

            InitiateAuthRequest initiateAuthRequest = new InitiateAuthRequest();
            initiateAuthRequest.withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH).withClientId("klua53s184h8i2ceu415vqemi")
                    .withAuthParameters(authParams);
            InitiateAuthResult result = cognitoClient.initiateAuth(initiateAuthRequest);
            return result.getAuthenticationResult();

    }

    @Override
    public void userLogout(String refreshToken) {

        try {
            cognitoClient.revokeToken(new RevokeTokenRequest().withToken(refreshToken).withClientId("klua53s184h8i2ceu415vqemi"));
        }catch (Exception e){

        }
    }

    @Override
    public Optional<User> patchUser(Long id, Map<Object, Object> fields){
        Optional<User> user = userRepository.findById(id);


        if(user.isPresent() ){
            fields.forEach((key, value) ->{
                Field field = ReflectionUtils.findField(User.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, user.get(), value);
            });


            userRepository.save(user.get());

        }
        return user ;

    }

}