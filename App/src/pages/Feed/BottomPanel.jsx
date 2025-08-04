import { open } from '@tauri-apps/plugin-dialog';

import MapSVG from "/src/assets/svg/map.svg";
import LensSVG from "/src/assets/svg/lens.svg";

function setDebug(text) {
	document.getElementById("debug").innerHTML = text
}

export default function BottomPanel({ page, setClip }) {
	
	if(page !== "feed") return <></>;
	
	async function openFilePicker() {
		const file = await open({
		  multiple: false,
		  directory: false,
		});
		console.log(file);
		setDebug(JSON.stringify(file))
		setClip({ file })
	}
	
	return (<div className="bottom-panel">
		<div className="ai-menu">
			<a><img src={ LensSVG }/></a>
		</div>
		<a id="debug">test</a>
		<div className="menu">
			<a onClick={openFilePicker}><img src={ MapSVG }/></a>
		</div>
	</div>)
}
