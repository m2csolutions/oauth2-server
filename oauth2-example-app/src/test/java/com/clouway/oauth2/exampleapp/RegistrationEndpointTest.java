package com.clouway.oauth2.exampleapp;

import com.clouway.oauth2.client.ClientRegistry;
import com.clouway.oauth2.client.RegistrationRequest;
import com.clouway.oauth2.client.RegistrationResponse;
import com.google.sitebricks.headless.Reply;
import org.hamcrest.MatcherAssert;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Ivan Stefanov <ivan.stefanov@clouway.com>
 */
public class RegistrationEndpointTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private ClientRegistry repository = context.mock(ClientRegistry.class);

  private RegistrationEndpoint endpoint = new RegistrationEndpoint(repository);

  @Test
  public void register() throws Exception {
    final RegistrationRequest registrationRequest = new RegistrationRequest("Ivan", "http://ivan.com/", "no description", "http://ivan.com/success");
    final RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO("Ivan", "http://ivan.com/", "no description", "http://ivan.com/success");

    final RegistrationResponse registrationResponse = new RegistrationResponse("123456789", "987654321");
    final RegistrationResponseDTO registrationResponseDTO = new RegistrationResponseDTO("123456789", "987654321");

    context.checking(new Expectations() {{
      oneOf(repository).register(registrationRequest);
      will(Expectations.returnValue(registrationResponse));
    }});

    Reply<RegistrationResponseDTO> response = endpoint.register(SiteBricksRequestMockery.mockRequest(registrationRequestDTO));
    MatcherAssert.assertThat(response, ReplyMatchers.containsValue(registrationResponseDTO));
  }
}