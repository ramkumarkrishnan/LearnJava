package com.opctlitom.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class PojoToJson {
    public static void main(String[] args) {
        String opctlCaRJsonFilePath = "/Users/ramkrish/github/LearnJava/corejava/"
         + "JSONConvert/resources/OpctlEventCreateAccessRequest.json";
        String opctlCoCJsonFilePath = "/Users/ramkrish/github/LearnJava/corejava/"
            + "JSONConvert/resources/OpctlEventCreateOperatorControl.json";
        String jsonStr =
            "{"
            + "\"eventType\": \"com.oraclecloud.operatorcontrol.createaccessrequest\","
            + "\"cloudEventsVersion\": \"0.1\","
            + "\"eventTypeVersion\": \"2.0\","
            + "\"source\": \"OperatorAccessControl\","
            + "\"eventTime\": \"2022-05-25T17:22:20Z\","
            + "\"contentType\": \"application/json\","
            + "\"data\": {"
            + "\"compartmentId\": \"ocid1.tenancy.oc1..aaaaaaaazxdmffivtoe32kvio5e2dcgz24re5rqbkis3452yi2e7tc3x2erq\","
            + "\"compartmentName\": \"dbaasprodintegtest\","
            + "\"resourceName\": \"scaqar05adm0102clu12\","
            + "\"resourceId\": \"ocid1.exadatainfrastructure.oc1.ap-chuncheon-1.ab4w4ljr46tyytihmindrbshch3jjhrxxpctq4eiaksakp4kqamluuwkzdga\","
            + "\"availabilityDomain\": \"ad1\","
            + "\"additionalDetails\": {"
            + "\"reason\": \"null ; \","
            + "\"exadatainfrastructure_ocid\": \"ocid1.exadatainfrastructure.oc1.ap-chuncheon-1.ab4w4ljr46tyytihmindrbshch3jjhrxxpctq4eiaksakp4kqamluuwkzdga\","
            + "\"reasonSummary\": \"exaccops-12345\","
            + "\"accessRequestId\": \"ocid1.opctlaccessrequest.oc1.ap-chuncheon-1.aaaaaaaasgcujttbnki5fuwajivwtyqlmbrowxckwgahdjzquc7urfljuj2q\","
            + "\"opCtlId\": \"ocid1.opctloperatorcontrol.oc1.ap-chuncheon-1.aaaaaaaa2yvm2qfodhiiptx2ubb3fghmetqhuaxyjmwb6zjleyvtworyalga\","
            + "\"accessRequest_url\": \"https://console.ap-chuncheon-1.oraclecloud.com/operator-access-control/access-requests/ocid1.opctlaccessrequest.oc1.ap-chuncheon-1.aaaaaaaasgcujttbnki5fuwajivwtyqlmbrowxckwgahdjzquc7urfljuj2q\","
            + "\"exadatainfrastructure_name\": \"scaqar05adm0102clu12\","
            + "\"opCtlName\": \"opstesting\""
            + "}"
            + "},"
            + "\"eventID\": \"47a36fcf-0d14-46f3-a6f5-564197754bb5\","
            + "\"extensions\": {"
            + "\"compartmentId\": \"ocid1.tenancy.oc1..aaaaaaaazxdmffivtoe32kvio5e2dcgz24re5rqbkis3452yi2e7tc3x2erq\""
            + "}"
            + "}";

        try (FileReader fileReader = new FileReader(opctlCaRJsonFilePath)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String inputJson = bufferedReader.lines().collect(Collectors.joining(" "));
            String outputJson = getItomJson(inputJson);

            // Gson gson = new Gson();
            // OpctlEvent evt = gson.fromJson(inputJson, OpctlEvent.class);
            // OpctlEvent evt = gson.fromJson(bufferedReader, OpctlEvent.class);
            // out.println("Input Json -> object -> Json:\n" + gson.toJson(evt));

            out.println("OutputJson:\n" + outputJson);
            out.println("\nTIME:" +
                LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getItomJson(String inputJson) {
        JsonElement jsonElement = JsonParser.parseString(inputJson);
        if (!jsonElement.isJsonObject()) {
            return null;
        }

        JsonObject eventObject = jsonElement.getAsJsonObject();
        SnowIncident snowIncident = new SnowIncident();

        snowIncident.setActive("true");
        snowIncident.setOpenedBy(eventObject.get("source").getAsString());
        snowIncident.setOpenedAt(eventObject.get("eventTime").getAsString());
        snowIncident.setDueDate(eventObject.get("eventTime").getAsString());
        snowIncident.setCompany("Oracle Corporation"); // NO OPCTL EVENT I/P
        snowIncident.setAssignedTo("Customer Name");   // NO OPCTL EVENT I/P
        snowIncident.setSeverity(String.valueOf(10));  // NO OPCTL EVENT I/P
        snowIncident.setImpact("Low");   // NO OPCTL EVENT INPUT
        snowIncident.setPriority("Low"); // NO OPCTL EVENT INPUT
        snowIncident.setUrgency("Low");  // NO OPCTL EVENT INPUT
        snowIncident.setState("Active"); // NO OPCTL EVENT INPUT
        snowIncident.setLocation("us-ashburn-1"); // SAMPLE - NO OPCTL I/P
        snowIncident.setFollowUp("None"); // NO OPCTL EVENT INPUT
        snowIncident.setComments("None"); // NO OPCTL EVENT INPUT

        JsonObject dataObject = eventObject.get("data").getAsJsonObject();
        JsonObject aDtlsObject =
            dataObject.get("additionalDetails").getAsJsonObject();

        String operation = eventObject.get("eventType").getAsString();

        String shortDescription = "{" + "Operation:" + operation;
        if (operation.contains("accessrequest")) {
            shortDescription +=
                ",accessRequestId:" + aDtlsObject.get("accessRequestId").getAsString()
                + ",Request_URL:" + aDtlsObject.get("accessRequest_url").getAsString()
                + ",ReasonSummary:" + aDtlsObject.get("reasonSummary").getAsString();
        }
        else if (operation.contains("createoperatorcontrol"))  {
            shortDescription +=
                ",operatorcontrol_ocid:" + aDtlsObject.get("operatorcontrol_ocid").getAsString()
                + ",operatorcontrol_name:" + aDtlsObject.get("operatorcontrol_name").getAsString();
        }
        shortDescription += "}";
        snowIncident.setShortDescription(shortDescription);

        if (operation.contains("accessrequest")) {
            String description = "{" +
                "eventID:" + eventObject.get("eventID").getAsString()
                + ",compartmentId:" + dataObject.get("compartmentId").getAsString()
                + ",compartmentName:" + dataObject.get("compartmentName").getAsString()
                + ",resourceId:" + dataObject.get("resourceId").getAsString()
                + ",resourceName:" + dataObject.get("resourceName").getAsString()
                + ",availabilityDomain:" + dataObject.get("availabilityDomain").getAsString()
                + ",exadatainfrastructure_ocid:" + aDtlsObject.get("exadatainfrastructure_ocid").getAsString()
                + ",exadatainfrastructure_name:" + aDtlsObject.get("exadatainfrastructure_name").getAsString()
                + ",accessRequestId:" + aDtlsObject.get("accessRequestId").getAsString()
                + ",opCtlId:" + aDtlsObject.get("opCtlId").getAsString()
                + ",opCtlName:" + aDtlsObject.get("opCtlName").getAsString()
                + ",reason:" + aDtlsObject.get("reason").getAsString()
                + "}";
            System.out.println("SnowIncident:" + description);
            snowIncident.setDescription(description);
        }

        Gson gson = new Gson();
        String snowJson = gson.toJson(snowIncident);
        System.out.println("\n\nSnowJson:\n" + snowJson);
        return snowJson;
    }
}

