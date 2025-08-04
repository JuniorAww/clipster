import { useState, useEffect, useMemo, useRef } from "react";
import { invoke } from "@tauri-apps/api/core";

import VideoSVG from "./assets/svg/video.svg";
import SettingsSVG from "./assets/svg/settings.svg";

import ColoredCanvas from './utils/ColoredCanvas.jsx'
import useWindowDimensions from './utils/useWindowDimensions.jsx'

import Feed from './pages/Feed.jsx'
import TopLine from './pages/Feed/TopLine.jsx'
import BottomPanel from './pages/Feed/BottomPanel.jsx'
import Auth from './pages/Auth.jsx'

import "./App.css";

function App() {
	const { height, width } = useWindowDimensions();
	
	const [ page, setPage ] = useState("auth");
	const [ transition, setTransition ] = useState(null)
	
	const [ username, setUsername ] = useState('BakaBakovic')
	
	const feed = useRef([])
	const [ clip, setClip ] = useState(null)
	
	useEffect(() => {
		
		/*setTimeout(() => {
			setPage('auth')
		}, 3500)*/
		
		return () => {}
	}, [])
	
	
	async function greet() {
		setGreetMsg(await invoke("greet", { name }))
	}
	
	const avatar = useMemo(() => "", [ username ])
	
	return (
	<>
		<Auth        page={page}
                     height={height} width={width}
					 username={username}/>
		<TopLine     page={page}
					 username={username} avatar={avatar}
		/>
		<Feed        page={page} 
                     height={height} width={width} clip={clip} feed={feed}
		/>
		<BottomPanel page={page} 
                     setClip={setClip}
		/>
	
		<svg xmlns="http://www.w3.org/2000/svg" version="1.1" height="0" width="0">
			<defs>
				<filter id="noise">
				    <feTurbulence type="fractalNoise" baseFrequency=".03" numOctaves="4" />
				    <feDisplacementMap in="SourceGraphic" scale="4" />
				</filter>
			</defs>
		</svg>
	</>
	);
}



export default App;
