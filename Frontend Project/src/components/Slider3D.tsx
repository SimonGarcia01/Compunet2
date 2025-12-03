import React from "react";
import "./Slider3D.css";
import { motion } from "framer-motion";

const SLIDES = [
  "/images/slider/gym_1.png",
  "/images/slider/gym_2.png",
  "/images/slider/gym_3.png",
  "/images/slider/gym_4.png",
  "/images/slider/gym_5.png",
  "/images/slider/gym_6.png",
  "/images/slider/gym_7.png",
  "/images/slider/gym_8.png",
  "/images/slider/gym_9.png",
  "/images/slider/gym_10.png",
];

export default function Slider3D() {
  return (
    <section className="banner relative overflow-hidden">
      {/* Purple gradient overlay */}
      <div className="absolute inset-0 bg-gradient-to-b from-transparent via-[#5453E0]/5 to-transparent z-10 pointer-events-none" />
      
      <div className="slider" style={{ "--quantity": SLIDES.length } as React.CSSProperties}>
        {SLIDES.map((src, i) => (
          <motion.div
            key={src}
            className="item"
            style={{ "--position": i + 1 } as React.CSSProperties}
            whileHover={{ scale: 1.1, z: 50 }}
            transition={{ duration: 0.3 }}
          >
            <img src={src} alt={`Entrenamiento ${i + 1}`} />
            {/* Purple glow on hover */}
            <div className="absolute inset-0 bg-gradient-to-br from-[#5453E0]/0 to-[#5453E0]/0 hover:from-[#5453E0]/20 hover:to-[#5453E0]/10 transition-all duration-300 rounded-lg" />
          </motion.div>
        ))}
      </div>

      <motion.div
        className="content relative z-20"
        initial={{ opacity: 0, y: 50 }}
        whileInView={{ opacity: 1, y: 0 }}
        viewport={{ once: true }}
        transition={{ duration: 0.8 }}
      >
        <motion.h1
          data-content="GYMAPP"
          className="relative"
          animate={{
            textShadow: [
              "0 0 20px rgba(84, 83, 224, 0.5)",
              "0 0 40px rgba(84, 83, 224, 0.8)",
              "0 0 20px rgba(84, 83, 224, 0.5)",
            ],
          }}
          transition={{
            duration: 3,
            repeat: Infinity,
            ease: "easeInOut"
          }}
        >
          GYMAPP
        </motion.h1>
        <motion.div
          className="author"
          initial={{ opacity: 0 }}
          whileInView={{ opacity: 1 }}
          viewport={{ once: true }}
          transition={{ delay: 0.3, duration: 0.8 }}
        >
          <h2 className="relative">
            TU MEJOR VERSIÓN
            <motion.span
              className="absolute -top-2 -right-8 text-4xl"
              animate={{
                rotate: [0, 15, -15, 0],
                scale: [1, 1.2, 1],
              }}
              transition={{
                duration: 2,
                repeat: Infinity,
                ease: "easeInOut"
              }}
            >
              ⚡
            </motion.span>
          </h2>
          <p>
            <b className="text-transparent bg-clip-text bg-gradient-to-r from-[#5453E0] to-[#7B7AFF]">
              Entrenamiento Inteligente
            </b>
          </p>
          <p>Transforma tu cuerpo y mente con tecnología de vanguardia</p>
        </motion.div>
      </motion.div>

      {/* Floating particles */}
      {[...Array(10)].map((_, i) => (
        <motion.div
          key={i}
          className="absolute w-2 h-2 bg-[#5453E0] rounded-full opacity-40"
          style={{
            left: `${Math.random() * 100}%`,
            top: `${Math.random() * 100}%`,
          }}
          animate={{
            y: [0, -100, 0],
            opacity: [0.4, 0.8, 0.4],
            scale: [1, 1.5, 1],
          }}
          transition={{
            duration: 3 + Math.random() * 2,
            repeat: Infinity,
            delay: Math.random() * 2,
            ease: "easeInOut"
          }}
        />
      ))}
    </section>
  );
}
