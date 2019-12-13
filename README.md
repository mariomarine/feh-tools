# feh-tools
## Fire Emblem: Heroes Tools

This repository is my collection of tools I can use to better understand my choices within FEH.

### Summon Simulator

The Summon Simulator is designed to give statistics on how many orbs it takes to get focus heroes (or other stars potentially?)

Things the Summon Simulator considers (when finished):
- The order when determining which discs to display are (for each orb):
 - Determine the rarity (*) of the hero
 - From the pool of the given rarity, randomly choose a hero
 - Display the color of the hero.
- Every five summons without a 5* hero will increase the next summoning session's chance at obtaining a 5* hero by 0.5%
- After 120 summons without a 5* hero, the next summoning session's chance at obtaining a 5* hero is 100%

The idea is to give the simulator a color, and ask it to "snipe" for a given color (summon repeatedly of only 1 color).

We should give output for:
- a percentage breakdown similar to The Odds in [this article](https://gamepress.gg/feheroes/should-you-pull-book-iv-begins-peony-and-more-edition)
- it would be nice to see how many off-color heroes were obtained
- break it down into specific heroes (aka, if there are multiple focus heroes of the same color, your odds are reduced)
- a summary of how many orbs it takes to summon 1 unit, and how many orbs it takes to summon 11 units (max merges)
