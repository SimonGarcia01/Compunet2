import { Button } from "@/components/ui/button";
import { ArrowRight, Sparkles, Zap, TrendingUp, Users } from "lucide-react";
import { useDispatch, useSelector } from "react-redux";
import type { RootState } from "@/store/index";
import LogoutButton from "./LogoutButton";
import { useNavigate } from "react-router-dom";
import { loginRequest } from "@/store/auth/actions";
import { motion } from "framer-motion";

export default function Hero() {
  const token = useSelector((state: RootState) => state.auth.token);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  
  const handleLogin = () => {
      dispatch(loginRequest());
      navigate("/login");
  };

  const containerVariants = {
    hidden: { opacity: 0 },
    visible: {
      opacity: 1,
      transition: {
        staggerChildren: 0.2,
        delayChildren: 0.1
      }
    }
  };

  const itemVariants = {
    hidden: { opacity: 0, y: 30 },
    visible: {
      opacity: 1,
      y: 0,
      transition: {
        duration: 0.8,
        ease: "easeOut"
      }
    }
  };

  return (
    <section className="relative h-screen flex items-center justify-center overflow-hidden">
      {/* Botón de logout en la esquina superior derecha si está logueado */}
      {token && (
        <div className="absolute top-6 right-6 z-20">
          <LogoutButton />
        </div>
      )}

      {/* Animated grid background with purple tint */}
      <div className="absolute inset-0 bg-[linear-gradient(to_right,#5453E0_0.5px,transparent_0.5px),linear-gradient(to_bottom,#5453E0_0.5px,transparent_0.5px)] bg-[size:40px_40px] opacity-20 animate-pulse-slow" />
      
      {/* Floating gradient orbs with purple */}
      <motion.div 
        className="absolute top-20 left-10 w-96 h-96 bg-[#5453E0]/30 rounded-full blur-[120px]"
        animate={{
          scale: [1, 1.2, 1],
          x: [0, 50, 0],
          y: [0, 30, 0],
        }}
        transition={{
          duration: 8,
          repeat: Infinity,
          ease: "easeInOut"
        }}
      />
      <motion.div 
        className="absolute bottom-20 right-10 w-[500px] h-[500px] bg-[#5453E0]/25 rounded-full blur-[140px]"
        animate={{
          scale: [1, 1.3, 1],
          x: [0, -40, 0],
          y: [0, -50, 0],
        }}
        transition={{
          duration: 10,
          repeat: Infinity,
          ease: "easeInOut",
          delay: 1
        }}
      />
      <motion.div 
        className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[600px] h-[600px] bg-[#5453E0]/10 rounded-full blur-[150px]"
        animate={{
          scale: [1, 1.4, 1],
          rotate: [0, 180, 360],
        }}
        transition={{
          duration: 15,
          repeat: Infinity,
          ease: "linear"
        }}
      />
      
      {/* Particle effects */}
      {[...Array(20)].map((_, i) => (
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
      
      <motion.div 
        className="relative z-10 max-w-6xl mx-auto px-6 text-center"
        variants={containerVariants}
        initial="hidden"
        animate="visible"
      >
        <motion.div 
          className="inline-flex items-center gap-2 mb-8 px-6 py-3 rounded-full border-2 border-[#5453E0]/30 bg-gradient-to-r from-[#5453E0]/10 to-[#5453E0]/5 backdrop-blur-md shadow-lg"
          variants={itemVariants}
        >
          <Sparkles className="w-5 h-5 text-[#5453E0] animate-pulse" />
          <span className="font-['Montserrat'] text-sm font-bold text-[#5453E0] tracking-wide">PLATAFORMA PREMIUM PARA ESTUDIANTES</span>
          <Zap className="w-5 h-5 text-[#5453E0] animate-pulse" />
        </motion.div>
        
        <motion.h1 
          className="font-['Montserrat'] font-black text-6xl md:text-8xl lg:text-9xl mb-8 tracking-tighter leading-none"
          variants={itemVariants}
        >
          <span className="block mb-2">TU ENTRENAMIENTO</span>
          <span className="block relative">
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-[#5453E0] via-[#7B7AFF] to-[#5453E0] animate-gradient bg-[length:200%_auto]">
              INTELIGENTE
            </span>
            <motion.span
              className="absolute -top-2 -right-4 text-4xl md:text-6xl"
              animate={{
                rotate: [0, 10, -10, 0],
                scale: [1, 1.1, 1],
              }}
              transition={{
                duration: 2,
                repeat: Infinity,
                ease: "easeInOut"
              }}
            >
              ⚡
            </motion.span>
          </span>
        </motion.h1>
        
        <motion.p 
          className="font-['Montserrat'] text-xl md:text-2xl lg:text-3xl mb-12 text-muted-foreground max-w-4xl mx-auto font-semibold leading-relaxed"
          variants={itemVariants}
        >
          La plataforma más avanzada con{" "}
          <span className="text-[#5453E0] font-bold">rutinas personalizadas</span>,{" "}
          <span className="text-[#5453E0] font-bold">seguimiento profesional</span> y{" "}
          <span className="text-[#5453E0] font-bold">estadísticas en tiempo real</span>
        </motion.p>
        
        <motion.div 
          className="flex flex-col sm:flex-row gap-6 justify-center items-center"
          variants={itemVariants}
        >
          <motion.div whileHover={{ scale: 1.05 }} whileTap={{ scale: 0.95 }}>
            <Button 
              size="lg" 
              className="font-['Montserrat'] font-black text-lg px-10 py-7 bg-gradient-to-r from-[#5453E0] to-[#7B7AFF] hover:from-[#7B7AFF] hover:to-[#5453E0] text-white border-0 shadow-2xl shadow-[#5453E0]/50 group transition-all duration-300 relative overflow-hidden"
              onClick={handleLogin}
            >
              <span className="relative z-10 flex items-center">
                COMENZAR AHORA 
                <ArrowRight className="w-6 h-6 ml-3 group-hover:translate-x-2 transition-transform" />
              </span>
              <motion.div
                className="absolute inset-0 bg-gradient-to-r from-white/20 to-transparent"
                animate={{
                  x: ['-100%', '100%'],
                }}
                transition={{
                  duration: 2,
                  repeat: Infinity,
                  ease: "linear"
                }}
              />
            </Button>
          </motion.div>
          
          <motion.div whileHover={{ scale: 1.05 }} whileTap={{ scale: 0.95 }}>
            <Button 
              size="lg" 
              variant="outline" 
              className="font-['Montserrat'] font-bold text-lg px-10 py-7 border-2 border-[#5453E0]/50 hover:border-[#5453E0] hover:bg-[#5453E0]/10 backdrop-blur-sm transition-all duration-300 shadow-lg"
            >
              VER DEMO
            </Button>
          </motion.div>
        </motion.div>

        {/* Stats preview */}
        <motion.div 
          className="flex flex-wrap items-center justify-center gap-8 mt-16"
          variants={itemVariants}
        >
          <motion.div 
            className="flex items-center gap-3 px-6 py-3 rounded-full bg-[#5453E0]/10 backdrop-blur-sm border border-[#5453E0]/20"
            whileHover={{ scale: 1.1, backgroundColor: "rgba(84, 83, 224, 0.2)" }}
          >
            <TrendingUp className="w-5 h-5 text-[#5453E0]" />
            <span className="font-['Montserrat'] font-bold text-sm text-[#5453E0]">+500 Estudiantes</span>
          </motion.div>
          <motion.div 
            className="flex items-center gap-3 px-6 py-3 rounded-full bg-[#5453E0]/10 backdrop-blur-sm border border-[#5453E0]/20"
            whileHover={{ scale: 1.1, backgroundColor: "rgba(84, 83, 224, 0.2)" }}
          >
            <Users className="w-5 h-5 text-[#5453E0]" />
            <span className="font-['Montserrat'] font-bold text-sm text-[#5453E0]">20+ Entrenadores</span>
          </motion.div>
          <motion.div 
            className="flex items-center gap-3 px-6 py-3 rounded-full bg-[#5453E0]/10 backdrop-blur-sm border border-[#5453E0]/20"
            whileHover={{ scale: 1.1, backgroundColor: "rgba(84, 83, 224, 0.2)" }}
          >
            <Zap className="w-5 h-5 text-[#5453E0]" />
            <span className="font-['Montserrat'] font-bold text-sm text-[#5453E0]">100% Gratis</span>
          </motion.div>
        </motion.div>
      </motion.div>

      {/* Scroll indicator */}
      <motion.div 
        className="absolute bottom-8 left-1/2 -translate-x-1/2 z-10"
        animate={{
          y: [0, 10, 0],
        }}
        transition={{
          duration: 2,
          repeat: Infinity,
          ease: "easeInOut"
        }}
      >
        <div className="w-6 h-10 border-2 border-[#5453E0]/50 rounded-full flex items-start justify-center p-2">
          <motion.div
            className="w-1.5 h-1.5 bg-[#5453E0] rounded-full"
            animate={{
              y: [0, 12, 0],
            }}
            transition={{
              duration: 2,
              repeat: Infinity,
              ease: "easeInOut"
            }}
          />
        </div>
      </motion.div>
    </section>
  );
}
