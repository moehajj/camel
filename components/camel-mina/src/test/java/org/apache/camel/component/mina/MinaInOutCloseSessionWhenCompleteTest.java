/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.mina;

import org.apache.camel.builder.RouteBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for close session when complete test.
 */
public class MinaInOutCloseSessionWhenCompleteTest extends BaseMinaTest {

    @Test
    public void testCloseSessionWhenComplete() {
        Object out
                = template.requestBody(String.format("mina:tcp://localhost:%1$s?sync=true&textline=true", getPort()), "Chad");
        assertEquals("Bye Chad", out);
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {

            public void configure() {
                from(String.format("mina:tcp://localhost:%1$s?sync=true&textline=true", getPort())).process(exchange -> {
                    String body = exchange.getIn().getBody(String.class);
                    exchange.getMessage().setBody("Bye " + body);
                    exchange.getMessage().setHeader(MinaConstants.MINA_CLOSE_SESSION_WHEN_COMPLETE, true);
                });
            }
        };
    }
}
