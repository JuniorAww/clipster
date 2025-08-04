import { useRef, useEffect } from "react"

function ColoredCanvas(props) {
	const canvasRef = useRef(null);
	
	const specShift = props.shift;
	const w = props.width
	const h = props.height
	
	useEffect(() => {
		const canvas = canvasRef.current;
		const ctx = canvas.getContext('2d')
		
		let nt = Math.random()
		let animationFrameId;
		const hueBase = props.hueBase
		const hueRange = props.hueRange
		const circles = Math.random() > 0.999
		
		async function draw(){
			let noiseScale = props.noiseScale || 5;
			let dotSize = props.dotSize || 1
			let gap = props.text ? 0 : 3;
			let shape = 0;
			
			const shift = specShift.current * (dotSize + gap)
			
			for(var x = 0; x<w; x+=dotSize+gap){
				for(var y = 0; y<h; y+=dotSize+gap){
					var yn = noise.perlin3((y + shift) / noiseScale, x/noiseScale, nt)*20;
					var cn = lerp(hueRange, yn*hueRange, 0.2);
					
					ctx.beginPath();
					ctx.fillStyle = "hsla("+(hueBase+cn)+",70%,70%,1)";
					if(circles) {
						ctx.arc(x,y,dotSize/2,0,Math.PI*2);
						ctx.fill();
					}
					else ctx.fillRect(x,y,dotSize,dotSize);
					/*
					} else if(shape === 2){
						ctx.moveTo(x+(dotSize/2),y+dotSize);
						ctx.lineTo(x, y);
						ctx.lineTo(x+dotSize,y);
						ctx.fill();
					} else if(shape === 0){
						if(y % ((gap + dotSize) * 2) === (gap+dotSize)) {
							ctx.moveTo(x+(dotSize/2),y);
							ctx.lineTo(x+dotSize, y+dotSize);
							ctx.lineTo(x,y+dotSize);
						} else {
							ctx.moveTo(x+(dotSize/2),y+dotSize);
							ctx.lineTo(x, y);
							ctx.lineTo(x+dotSize,y);
						}
						ctx.fill();
					}*/
					ctx.closePath();
				}
			}
			
			if(props.text) {
				ctx.globalCompositeOperation = "destination-in"
				
				ctx.fillStyle = "white";
				ctx.font = '900 12px Inter'
				ctx.textAlign = "center"
				ctx.fillText(props.text, 18, 10);
				
				ctx.globalCompositeOperation = "source-over"
			}
			
			if(props.text) {
				await new Promise(r => setTimeout(r, 1000))
				nt += 0.2
			}
			else nt += 0.003
			
			animationFrameId = requestAnimationFrame(draw)
		}
		
		draw()
		
		return () => cancelAnimationFrame(animationFrameId)
	}, [])
	
	return <canvas style={props.style} width={w} height={h} ref={canvasRef} />;
}

function lerp(x1, x2, n) {
  return (1 - n) * x1 + n * x2;
}

export default ColoredCanvas

