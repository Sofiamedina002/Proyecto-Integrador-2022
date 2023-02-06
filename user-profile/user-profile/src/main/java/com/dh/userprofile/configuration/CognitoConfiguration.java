package com.dh.userprofile.configuration;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class CognitoConfiguration {

  private final String awsProfileName;
  private final String awsRegion;

  public CognitoConfiguration(@Value(value = "${aws.profile.name}") String awsProfileName , @Value(value = "${aws.region}") String awsRegion) {
    this.awsProfileName = awsProfileName;
    this.awsRegion = awsRegion;
  }


  @Bean
  @Profile(value="default")
  public AWSCognitoIdentityProvider cognitoClient(){
    return AWSCognitoIdentityProviderClientBuilder
      .standard()
      .build();
  }

}
