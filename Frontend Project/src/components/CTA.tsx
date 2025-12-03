import { Button } from "@/components/ui/button";
import { loginRequest } from "@/store/auth/actions";
import { ArrowRight, Sparkles, Zap, CheckCircle2, Shield, Gift } from "lucide-react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";

export default function CTA() {
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
        staggerChildren: 0.2
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

  const trustBadges = [
    { icon: Shield, text: "Acceso Seguro" },
    { icon: Gift, text: "Sin Tarjeta" },
    { icon: CheckCircle2, text: "Gratis para Estudiantes" }
  ];

  return (
    <section className="relative py-40 bg-gradient-to-b from-background via-[#5453E0]/20 to-foreground text-background overflow-hidden">
      {/* Animated grid background */}
      <div className="absolute inset-0 bg-[linear-gradient(to_right,#ffffff08_1px,transparent_1px),linear-gradient(to_bottom,#ffffff08_1px,transparent_1px)] bg-[size:50px_50px] opacity-30" />
      
      {/* Gradient orbs with purple */}
      <motion.div
        className="absolute top-0 left-0 w-[600px] h-[600px] bg-[#5453E0]/30 rounded-full blur-[150px]"
        animate={{
          scale: [1, 1.3, 1],
          x: [0, 100, 0],
          y: [0, 50, 0],
        }}
        transition={{
          duration: 8,
          repeat: Infinity,
          ease: "easeInOut"
        }}
      />
      <motion.div
        className="absolute bottom-0 right-0 w-[700px] h-[700px] bg-[#7B7AFF]/25 rounded-full blur-[180px]"
        animate={{
          scale: [1, 1.4, 1],
          x: [0, -80, 0],
          y: [0, -60, 0],
        }}
        transition={{
          duration: 10,
          repeat: Infinity,
          ease: "easeInOut",
          delay: 1
        }}
      />
      
      <motion.div
        className="relative z-10 max-w-5xl mx-auto px-6 text-center"
        variants={containerVariants}
        initial="hidden"
        whileInView="visible"
        viewport={{ once: true }}
      >
        <motion.div
          className="inline-flex items-center gap-2 mb-8 px-6 py-3 rounded-full border-2 border-background/30 bg-background/10 backdrop-blur-md"
          variants={itemVariants}
        >
          <Sparkles className="w-5 h-5 text-background" />
          <span className="font-['Montserrat'] text-sm font-bold text-background tracking-wide">TU MOMENTO ES AHORA</span>
          <Zap className="w-5 h-5 text-background animate-pulse" />
        </motion.div>
        
        <motion.h2
          className="font-['Montserrat'] font-black text-5xl md:text-7xl lg:text-8xl mb-8 tracking-tighter leading-none"
          variants={itemVariants}
        >
          <span className="block mb-2">COMIENZA</span>
          <span className="block text-transparent bg-clip-text bg-gradient-to-r from-background via-white to-background animate-gradient">
            HOY
          </span>
        </motion.h2>
        
        <motion.p
          className="font-['Montserrat'] text-xl md:text-2xl lg:text-3xl mb-12 opacity-95 font-semibold leading-relaxed max-w-3xl mx-auto"
          variants={itemVariants}
        >
          Únete a la revolución del fitness inteligente.
          <br />
          <span className="text-[#5453E0] font-black">Tu transformación empieza ahora.</span>
        </motion.p>
        
        <motion.div
          className="flex flex-col sm:flex-row gap-6 justify-center items-center mb-16"
          variants={itemVariants}
        >
          <motion.div
            whileHover={{ scale: 1.05 }}
            whileTap={{ scale: 0.95 }}
          >
            <Button
              size="lg"
              className="font-['Montserrat'] font-black text-lg px-12 py-8 bg-gradient-to-r from-background to-white text-foreground hover:from-white hover:to-background border-0 shadow-2xl shadow-background/30 group transition-all duration-300 relative overflow-hidden"
              onClick={handleLogin}
            >
              <span className="relative z-10 flex items-center">
                ACCEDER CON CUENTA INSTITUCIONAL
                <ArrowRight className="w-6 h-6 ml-3 group-hover:translate-x-2 transition-transform" />
              </span>
              <motion.div
                className="absolute inset-0 bg-gradient-to-r from-transparent via-white/20 to-transparent"
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
        </motion.div>
        
        {/* Trust badges */}
        <motion.div
          className="flex flex-wrap items-center justify-center gap-8"
          variants={itemVariants}
        >
          {trustBadges.map((badge, index) => (
            <motion.div
              key={index}
              className="flex items-center gap-3 px-6 py-3 rounded-full bg-background/10 backdrop-blur-sm border border-background/20 hover:bg-background/20 transition-all duration-300"
              whileHover={{ scale: 1.1, y: -5 }}
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ delay: index * 0.1 }}
            >
              <badge.icon className="w-5 h-5 text-background" />
              <span className="font-['Montserrat'] text-sm font-bold text-background">{badge.text}</span>
            </motion.div>
          ))}
        </motion.div>
      </motion.div>

      {/* Floating particles */}
      {[...Array(15)].map((_, i) => (
        <motion.div
          key={i}
          className="absolute w-2 h-2 bg-background/40 rounded-full"
          style={{
            left: `${Math.random() * 100}%`,
            top: `${Math.random() * 100}%`,
          }}
          animate={{
            y: [0, -150, 0],
            opacity: [0.4, 1, 0.4],
            scale: [1, 1.5, 1],
          }}
          transition={{
            duration: 4 + Math.random() * 2,
            repeat: Infinity,
            delay: Math.random() * 2,
            ease: "easeInOut"
          }}
        />
      ))}
    </section>
  );
}
