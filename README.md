<div align="center">
<h1>Action Progress Plugin (BETA)</h1>

Displays an indicator showing how much progress you've made on your current action, and how long until it will finish.

<img alt="img.png" src="demo.png" style="align: center;"/>
</div>

# Changes

- `1.01`
	- Respects the user-defined infobox background colour.
	- The "Ignore single action" property was mislabeled, it was inverted.
- `1.02`
	- Track Tempoross activities (Cooking and filling crates).
	- Interrupt action when wearing or removing equipment.
- `1.03`
	- Rewritten a lot of core components.
	- Many actions added.
	- Many ingredient checks added, giving more accurate estimations of how many actions can be performed.
	- Many customizations added, all actions can be enabled or disabled.
	- Option to show skill icons instead of product icons (For example, Herblore icon instead of the potion being made).
- `1.03.1`
	- Fixed various issues with 1.03.
	- IDQuery API moved to testing and replaced with constant ID arrays.
		- This API had unforeseen issues due to database classes not existing in release environments.
		- Tests have been put in place to ensure that these constants are kept up-to-date.
	- Fixed TemporossDetector.
	- Fixed ItemClickDetector.
- `1.04`
    - Added interruption when hit.
    - Added interruption when pest control portals drop.
  	- Updated for RuneLite version 1.8.30.
- `1.05`
	- Add double ammo mould support.
	- Add settings for customizable colors for progress bar.
	- Updated for RuneLite version 1.9.13.3.
- `1.06`
	- Add support for weaving.
	- Ability to resize overlay.
	- Detect when chemistry amulet breaks.