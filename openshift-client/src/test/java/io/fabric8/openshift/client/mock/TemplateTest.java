/**
 * Copyright (C) 2015 Red Hat, Inc.                                        
 *                                                                         
 * Licensed under the Apache License, Version 2.0 (the "License");         
 * you may not use this file except in compliance with the License.        
 * You may obtain a copy of the License at                                 
 *                                                                         
 *         http://www.apache.org/licenses/LICENSE-2.0                      
 *                                                                         
 * Unless required by applicable law or agreed to in writing, software     
 * distributed under the License is distributed on an "AS IS" BASIS,       
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and     
 * limitations under the License.
 */

package io.fabric8.openshift.client.mock;

import io.fabric8.openshift.api.model.Template;
import io.fabric8.openshift.api.model.TemplateBuilder;
import io.fabric8.openshift.api.model.TemplateList;
import io.fabric8.openshift.api.model.TemplateListBuilder;
import io.fabric8.openshift.client.OpenShiftClient;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TemplateTest extends OpenShiftMockServerTestBase {

  @Test
  public void testList() {
    expectAndReturnAsJson("/oapi/v1/namespaces/test/templates", 200, new TemplateListBuilder().build());
    expectAndReturnAsJson("/oapi/v1/namespaces/ns1/templates", 200, new TemplateListBuilder()
      .addNewItem().and()
      .addNewItem().and().build());

    expectAndReturnAsJson("/oapi/v1/templates", 200, new TemplateListBuilder()
      .addNewItem().and()
      .addNewItem().and()
      .addNewItem()
      .and().build());

    OpenShiftClient client = getOpenshiftClient();

    TemplateList templateList = client.templates().list();
    assertNotNull(templateList);
    assertEquals(0, templateList.getItems().size());

    templateList = client.templates().inNamespace("ns1").list();
    assertNotNull(templateList);
    assertEquals(2, templateList.getItems().size());

    templateList = client.templates().inAnyNamespace().list();
    assertNotNull(templateList);
    assertEquals(3, templateList.getItems().size());
  }


  @Test
  public void testGet() {
    expectAndReturnAsJson("/oapi/v1/namespaces/test/templates/tmpl1", 200, new TemplateBuilder()
      .withNewMetadata().withName("tmpl1").endMetadata()
      .build());

    expectAndReturnAsJson("/oapi/v1/namespaces/ns1/templates/tmpl2", 200, new TemplateBuilder()
      .withNewMetadata().withName("tmpl2").endMetadata()
      .build());

    OpenShiftClient client = getOpenshiftClient();

    Template template = client.templates().withName("tmpl1").get();
    assertNotNull(template);
    assertEquals("tmpl1", template.getMetadata().getName());

    template = client.templates().withName("tmpl2").get();
    assertNull(template);

    template = client.templates().inNamespace("ns1").withName("tmpl2").get();
    assertNotNull(template);
    assertEquals("tmpl2", template.getMetadata().getName());
  }


  @Test
  public void testDelete() {
    expectAndReturnAsJson("/oapi/v1/namespaces/test/templates/tmpl1", 200, new TemplateBuilder().build());
    expectAndReturnAsJson("/oapi/v1/namespaces/ns1/templates/tmpl2", 200, new TemplateBuilder().build());

    OpenShiftClient client = getOpenshiftClient();

    Boolean deleted = client.templates().withName("tmpl1").delete();
    assertNotNull(deleted);

    deleted = client.templates().withName("tmpl2").delete();
    assertFalse(deleted);

    deleted = client.templates().inNamespace("ns1").withName("tmpl2").delete();
    assertTrue(deleted);
  }

}
