import './Feed.css'

import { readFile, BaseDirectory } from '@tauri-apps/plugin-fs';

import { useState, useEffect, useRef } from "react";

import ColoredCanvas from '../utils/ColoredCanvas.jsx'



function Feed({ page, width, height, clip }) {
	const yShift = useRef(0)
	const previousY = useRef(null)
	const dragStartY = useRef(null)
	const currentVideo = useRef(null)

	useEffect(() => {
		if(!clip) return;
		
		if(currentVideo.current === null) {
			currentVideo.current = 0
		}
		
		const a = document.getElementById('debug')
		a.innerHTML = Math.random()
		
		async function load() {
			const buffer = await readFile(clip.file);
			
			const blob = new Blob([buffer], { type: 'video/mp4' });
			
			const url = URL.createObjectURL(blob);
			
			const videos = document.getElementById('videos')
			
			const videoElement = document.createElement('video')
			videoElement.id = "video"
			
			videos.appendChild(videoElement)
			
			videoElement.src = url;
			videoElement.play()
			
			videoElement.addEventListener('ended', function(){
				videoElement.currentTime = 0;
				videoElement.play();
			});
			
			const nextVideo = document.createElement('video')
			nextVideo.id = "video"
			
			videos.appendChild(nextVideo)
			nextVideo.src = url
		}
		
		load()
	}, [ clip ])
	
	if(page !== "feed") return <></>;

	async function move(e) {
		e.preventDefault();
		const videos = document.getElementById("videos")
		
		let touch = e.targetTouches[0]
		const a = document.getElementById('debug')
		
		if(clip) {
			if(dragStartY.current === null) dragStartY.current = touch.screenY
			else {
				const shifted = touch.screenY - dragStartY.current
				a.innerHTML = shifted
				videos.style.top = shifted + 'px'
			}
			
		} else {
		
			if(previousY.current === null) previousY.current = touch.screenY
			else {
				const yShift_ = touch.screenY - previousY.current
				previousY.current = touch.screenY
				yShift.current = yShift.current - yShift_ / 30
			}
		
		}
	}
	
	function moveEnd(e) {
		e.preventDefault()
		previousY.current = null
		dragStartY.current = null
		const videos = document.getElementById("videos")
		if(videos) {
			videos.style.top = currentVideo.current * height + 'px'
		}
	}
	
	async function getClip(URL) {
		//const res = await fetch("http://192.168.3.12:8080/api/clips/0")
		const res = await fetch(URL)
		const clip = await res.json();
		
		setDebug(clip.resolutions[0].link)
		loadClip(clip.resolutions[0].link)
	}
	
	async function loadClip(src) {
		const video = document.querySelector("#video-block");
		
		const source = document.createElement("source");
		source.setAttribute('src', src)
		source.setAttribute('type', 'video/mp4')
		
		video.appendChild(source)
		video.play()
	}
	
	
	return (<div>
		<div onTouchMove={move} onTouchEnd={moveEnd} className="feed">
			{ clip ?
				<div id="videos"></div>
			: "" }
		</div>
		
		{ !clip ?
		<ColoredCanvas style={{ position: 'absolute', top: 0, left: 0, zIndex: -1, opacity: 0.05 }} dotSize={40} noiseScale={150} hueBase={200} hueRange={60} height={height} width={width} shift={yShift}/>
		: "" }
	</div>)
}

export default Feed
