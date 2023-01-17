# Documents
This plugin helps the management of the official documents of a player in a Roleplay Server, like ID Card (With player info as playtime and job) or health insurance card.

## Config

```
inventory: #inventories
  secretary: #secretary inventory
    title: "Computer segreteria"
    items:
      empty:
        '1':
          material: GRAY_STAINED_GLASS_PANE
          name: " "
          slot:
            row: 0
            column: 0
      identity:
        material: PLAYER_HEAD
        data: 2102
        name: "&bStampa carta d'identità"
        lore:
          - "&cClicca qui per stampare la carta d'identità!"
        slot:
          row: 2
          column: 2
      health:
        material: PLAYER_HEAD
        data: 2103
        name: "&bStampa tessera sanitaria"
        lore:
          - "&cClicca qui per stampare la tessera sanitaria!"
        slot:
          row: 2
          column: 2

  hospital: #hospital inventory
    title: "Computer Ospedale"
    items:
      empty:
        '1':
          material: GRAY_STAINED_GLASS_PANE
          name: " "
          slot:
            row: 0
            column: 0
      register:
        material: PLAYER_HEAD
        data: 2100
        name: "&bRegistra tessera sanitaria"
        lore:
          - "&cClicca qui per registrare la tessera sanitaria!"
        slot:
          row: 2
          column: 2
      check:
        material: PLAYER_HEAD
        data: 2101
        name: "&Scansiona tessera sanitaria"
        lore:
          - "&cClicca qui per scansionare la tessera sanitaria!"
        slot:
          row: 3
          column: 2
  select: #player select inventory
    title: "Scegli giocatore"

expires-in-days: 14 #expire time for cards

items:
  identity:
    material: STONE
    name: "Carta d'identità"
    lore:
      - "Proprietario: %player%"
      - "Scadenza: %expiration%"
  health:
    material: REDSTONE_BLOCK
    name: "Tessera Sanitaria"
    lore:
      - "Proprietario: %player%"
      - "Scadenza: %expiration%"


identity-book: #identity book, each section is as page
  '1':
    - "CARTA D'IDENTITA"
    - ""
    - ""
    - "LAVORO: %job%"
    - ""
    - "Tempo settimanale: %time%"

messages:
  wrong-type: "&cNon esiste un computer di questo tipo!"
  computer-added: "&2Computer registrato!"
  isnt-health-card: "&cDevi avere in mano la tessera sanitaria!"
  cant-break: "&cAccovacciati per rompere questo blocco!"
  computer-removed: "&2Compute rimosso!"
  card-printed: "Carta stampata!"
  card-registered: "La tessera sanitaria è registrata"
  card-not-registered: "La tessera sanitaria non è registrata!"
  card-registered-successfully: "Tessera sanitaria registrata correttamente!"
```

## Screenshots
![2023-01-17_15 24 09](https://user-images.githubusercontent.com/39953274/212928834-9c5e0772-aacf-4000-b92d-88958b88cfd8.png)
![2023-01-17_15 23 56](https://user-images.githubusercontent.com/39953274/212928850-49a78746-9430-4a51-9968-53167780d422.png)
![2023-01-17_15 23 51](https://user-images.githubusercontent.com/39953274/212928854-ce8a7baf-84e2-470c-8f3f-be31adde1067.png)
![2023-01-17_15 23 45](https://user-images.githubusercontent.com/39953274/212928861-f4bb205d-4afc-4c63-b44e-26082e866364.png)
![2023-01-17_15 22 56](https://user-images.githubusercontent.com/39953274/212928865-5df5f809-829f-4d83-aaf8-2ce84dd65e75.png)
![2023-01-17_15 22 55](https://user-images.githubusercontent.com/39953274/212928870-c115705b-458f-4982-8d6b-81887ce45672.png)
![2023-01-17_15 22 49](https://user-images.githubusercontent.com/39953274/212928874-6eb2742d-71cd-42b6-8f33-4068f654e3e0.png)
![2023-01-17_15 22 48](https://user-images.githubusercontent.com/39953274/212928876-e988bdd8-5659-4c50-9f89-2d5e917b3d43.png)
![2023-01-16_12 31 03](https://user-images.githubusercontent.com/39953274/212928882-02b6aaa4-07b3-464d-a3f9-e9c0e332791d.png)
![2023-01-16_12 30 59](https://user-images.githubusercontent.com/39953274/212928883-78eeda8f-7bca-4a7e-bb5f-8605ef60e814.png)
