import { useRef } from 'react'
import './Auth.css'

export default function Auth({ page, width }) {
	const letters = useRef(0)
	
	if(page !== "auth") return <></>;
	
	return (<>
		<Background/>
		<div className="centered-container"
			style={{ maxWidth: width / 1.2 + 'px' }}>
			<h1 className="text-a">А вы у нас впервые?</h1>
			<div className="tag-input">
				<a className="left">@</a>
				<input/>
				<a className="right">-></a>
				<div className="max-tag-size">
					{ letters.current }/30
				</div>
			</div>
			<a className="tag-input-description">Ваше уникальное имя</a>
			<h1 className="text-or">ИЛИ</h1>
			<div className="button-sms">Вход по SMS</div>
			<div className="login-row">
				<div className="button-google">
				G
				</div>
				<div className="button-vk">
				VK
				</div>
			</div>
		</div>
	</>)
}

const Background = () => <>
	<style>{`
		.auth-background {
			position: absolute;
			top: 0;
			left: 0;
			z-index: -2;
			opacity: 0.1;
			background: url("/src/assets/photos/placeholder.png");
			background-size: cover;
			width: 100vw;
			height: 100vh;
		}
	`}</style>
	<div className="auth-background"/>
</>
