/*
 * Copyright (C) Scott Cranton and Jakub Korab
 * https://github.com/CamelCookbook
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camelcookbook.security.signatures;

import org.apache.camel.builder.RouteBuilder;

/**
 * Demonstrates the use of public and private keys to digitally sign a message payload.
 */
public class SignaturesRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:sign")
            .log("Signing message")
            .to("crypto:sign://usingKeystore?keystore=#keyStore&alias=system_a&password=keyPasswordA")
            .to("log:out?showHeaders=true")
            .log("Message signed")
            .to("mock:signed")
            .to("direct:verify");

        from("direct:verify")
            .log("Verifying message")
            .to("crypto:verify//usingKeystore?keystore=#trustStore&alias=system_a")
            .log("Message verified")
            .to("mock:verified");
    }
}
